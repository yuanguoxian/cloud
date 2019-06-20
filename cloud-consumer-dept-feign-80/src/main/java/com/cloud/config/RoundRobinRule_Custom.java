package com.cloud.config;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

public class RoundRobinRule_Custom extends AbstractLoadBalancerRule {

	private AtomicInteger nextServerCyclicCounter;
	private int total = 0; // 总共被调用都次数
	private int currIndex = 0; // 当前被调用的server 的下标
	private static Logger log = LoggerFactory.getLogger(RoundRobinRule_Custom.class);

	public RoundRobinRule_Custom() {
		nextServerCyclicCounter = new AtomicInteger(0);
	}

	public RoundRobinRule_Custom(ILoadBalancer lb) {
		this();
		setLoadBalancer(lb);
	}

	public Server choose(ILoadBalancer lb, Object key) {
		if (lb == null) {
			log.warn("no load balancer");
			return null;
		}

		Server server = null;
		int count = 0;
		while (server == null && count++ < 10) {
			List<Server> reachableServers = lb.getReachableServers();
			List<Server> allServers = lb.getAllServers();
			int upCount = reachableServers.size();
			int serverCount = allServers.size();

			if ((upCount == 0) || (serverCount == 0)) {
				log.warn("No up servers available from load balancer: " + lb);
				return null;
			}

//			int nextServerIndex = incrementAndGetModulo(serverCount);
//			server = allServers.get(nextServerIndex);
			if(total++ < 5) {
				// 循环获取所有可用服务中都某一个服务
				server = reachableServers.get(currIndex);
			}else {
				total = 0;
				currIndex++;
				if(currIndex == reachableServers.size()) {
					currIndex = 0;
				}
			}
			if (server == null) {
				/* Transient. */
				Thread.yield();
				continue;
			}

			if (server.isAlive() && (server.isReadyToServe())) {
				return (server);
			}

			// Next.
			server = null;
		}

		if (count >= 10) {
			log.warn("No available alive servers after 10 tries from load balancer: " + lb);
		}
		return server;
	}

	/**
	 * Inspired by the implementation of {@link AtomicInteger#incrementAndGet()}.
	 *
	 * @param modulo The modulo to bound the value of the counter.
	 * @return The next value.
	 */
	@SuppressWarnings("unused")
	private int incrementAndGetModulo(int modulo) {
		for (;;) {
			int current = nextServerCyclicCounter.get();
			int next = (current + 1) % modulo;
			if (nextServerCyclicCounter.compareAndSet(current, next))
				return next;
		}
	}

	@Override
	public Server choose(Object key) {
		return choose(getLoadBalancer(), key);
	}

	@Override
	public void initWithNiwsConfig(IClientConfig clientConfig) {

	}

}
