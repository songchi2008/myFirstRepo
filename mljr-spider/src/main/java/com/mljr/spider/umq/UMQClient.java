/**
 * 
 */
package com.mljr.spider.umq;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ucloud.umq.action.MessageAction;
import com.ucloud.umq.action.RoleAction;
import com.ucloud.umq.action.UmqConnection;
import com.ucloud.umq.client.ServerResponseException;
import com.ucloud.umq.common.ServiceAttributes;
import com.ucloud.umq.model.Message;
import com.ucloud.umq.model.Role;

/**
 * @author Ckex zha </br>
 *         2016年11月9日,下午3:10:55
 *
 */
public class UMQClient {

	private UMQClient() {
		super();
		initConnection();
	}

	private void initConnection() {
		String publicKey = ServiceAttributes.getPublicKey();
		String privateKey = ServiceAttributes.getPrivateKey();
		UmqConnection.NewConnection(publicKey, privateKey);
	}

	private static class UMQClientHolder {
		private static final UMQClient instance = new UMQClient();
	}

	public static final UMQClient getInstence() {
		return UMQClientHolder.instance;
	}

	public Message getMessage(String region, String role, String queueId) throws ServerResponseException {
		if (StringUtils.isBlank(region)) {
			region = ServiceAttributes.getRegion();
		}
		if (StringUtils.isBlank(role)) {
			role = ServiceAttributes.getRole();
		}
		if (StringUtils.isBlank(queueId)) {
			throw new IllegalArgumentException("QueueID can't be null.");
		}
		// List<Role> consumers = RoleAction.createRole(region, queueId, 1,
		// role);
		// Role consumer = consumers.get(0);
		// return MessageAction.pullMsg(region, queueId, consumer.getId(),
		// consumer.getToken());
		return MessageAction.pullMsg(region, queueId, ServiceAttributes.getConsumerId(),
				ServiceAttributes.getConsumerToken());
	}

	public boolean ackMsg(String region, String role, String queueId, String msgId) throws ServerResponseException {
		if (StringUtils.isBlank(region)) {
			region = ServiceAttributes.getRegion();
		}
		if (StringUtils.isBlank(queueId)) {
			throw new IllegalArgumentException("QueueID can't be null.");
		}
		List<Role> consumers = RoleAction.createRole(region, queueId, 1, role);
		Role consumer = consumers.get(0);
		return MessageAction.ackMsg(region, queueId, consumer.getId(), msgId);
	}
}
