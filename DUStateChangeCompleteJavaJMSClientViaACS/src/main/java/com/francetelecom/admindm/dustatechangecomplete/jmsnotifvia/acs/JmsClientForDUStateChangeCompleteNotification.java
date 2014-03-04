/**
 * Product Name : Modus TR-069 Orange
 *
 * Copyright c 2014 Orange
 *
 * This software is distributed under the Apache License, Version 2.0
 * (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 or see the "license.txt" file for
 * more details
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author: Marc Douet - Orange
 * Mail: marc.douet@orange.com
 * Author: Antonin Chazalet - Orange
 * Mail: antonin.chazalet@orange.com;antonin.chazale@gmail.com
 */

package com.francetelecom.admindm.dustatechangecomplete.jmsnotifvia.acs;

import java.security.InvalidParameterException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

/**
 * @author: Marc Douet
 * @mail: marc.douet@orange.com
 * @author: Antonin Chazalet
 * @mail: antonin.chazalet@orange.com,antonin.chazalet@gmail.com
 */
public class JmsClientForDUStateChangeCompleteNotification {

	private final static Logger logger = Logger
			.getLogger(JmsClientForDUStateChangeCompleteNotification.class.getName());

	// URL of the JMS server
	private static String url = null;

	// Name of the queue we will receive messages from
	// private static String subject = "REGISTRATION_NOTICE";
	private static String subject = "NotificationDUStateChangeComplete_queue";

	public static void main(final String[] args) throws JMSException {
		// Getting JMS connection from the server
		logger.debug("Getting JMS connection from the server");

		if (url == null) {
			throw new InvalidParameterException("Fill url: " + url + ".");
		}
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();

		// Creating session for sending messages
		logger.debug("Creating session for seding messages");
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// Getting the queue
		logger.debug("Getting the queue");
		Destination destination = session.createQueue(subject);

		// MessageConsumer is used for receiving (consuming) messages
		logger.debug("MessageConsumer is used for receiving (consuming) messages");
		MessageConsumer consumer = session.createConsumer(destination);

		// Here we receive the message.
		// By default this call is blocking, which means it will wait
		// for a message to arrive on the queue.
		logger.debug("Here we receive the message");
		Message message = consumer.receive();

		// There are many types of Message and TextMessage
		// is just one of them. Producer sent us a TextMessage
		// so we must cast to it to get access to its .getText()
		// method.
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;

			// -----
			// install qui fonctionne :
			//
			// 2013-04-12 15:33:54 INFO
			// JmsClientForDUStateChangeCompleteNotification:192 - Received
			// message:
			// id:580002;
			// model:Device-HomeAPI;
			// serial:AZERTY_CHANGEDUSTATE_NBBSORA;
			// params:undefined;
			// uuid:34;
			// deploymentUnitRef:34;
			// version:1.0.0;
			// resolved:false;
			// executionUnitRefList:There is no way to get this info.;
			// startTime:2013-04-12T15:33:18Z;
			// completeTime:2013-04-12T15:33:19Z;
			// currentState:Installed;
			// fault:com.netopia.nbbs.tr69.msg.DeploymentUnitFaultStruct@f9ede6d;
			// result.faultCode:NO_FAULT;
			// result.faultString:null;
			// extra-comment:DUStateChangeCompleted message indicates successful
			// install, or update.;
			// msg.getCommandKey():CMDKEY_580002_INST_113312153312444

			// -----
			// install qui échoue :
			//
			// 2013-04-12 15:38:24 INFO
			// JmsClientForDUStateChangeCompleteNotification:199 - Received
			// message:
			// id:580002;
			// model:Device-HomeAPI;
			// serial:AZERTY_CHANGEDUSTATE_NBBSORA;
			// params:undefined;
			// uuid:undefined;
			// deploymentUnitRef:null;
			// version:null;
			// resolved:false;
			// executionUnitRefList:There is no way to get this info.;
			// startTime:2013-04-12T15:37:59Z;
			// completeTime:2013-04-12T15:37:59Z;
			// currentState:Failed;
			// fault:com.netopia.nbbs.tr69.msg.DeploymentUnitFaultStruct@6e30562b;
			// result.faultCode:DUPLICATE_DEPLOYMENT_UNIT;
			// result.faultString:Duplicate Deployment Unit: Bundle (located at
			// url:
			// http://archive.apache.org/dist/felix/org.apache.felix.http.jetty-1.0.0.jar)
			// is already deployed in EE.;
			// extra-comment:DUStateChangeCompleted message indicates failure.;
			// msg.getCommandKey():CMDKEY_580002_INST_113312153752699

			// -----
			// update qui fonctionne
			//
			// 2013-03-04 18:04:02 INFO
			// JmsClientForDUStateChangeCompleteNotification: - Received
			// message:
			// id:530006;model:Device-HomeAPI;serial:AZERTY_CHANGEDUSTATE_NBBSORA;
			// params:undefined;uuid:UUID_value;deploymentUnitRef:32;version:1.0.0;
			// resolved:false;executionUnitRefList:There is no way to get this
			// info.;startTime:2013-03-04T18:03:46Z;completeTime:2013-03-04T18:03:47Z;
			// currentState:Installed;
			// fault:com.netopia.nbbs.tr69.msg.DeploymentUnitFaultStruct@2dd31aac;
			// result.faultCode:NO_FAULT;
			// result.faultString:null;
			// extra-comment:DUStateChangeCompleted message indicates successful
			// install, or update.;
			// msg.getCommandKey():530006_TODO DEFINE THE COMMANDKEY!_UPDATE

			// -----
			// update qui ne fonctionne pas
			//

			// TODO AAA: pb. quand un update échoue, TR69/157 dit
			// currentState:Installed... Du coup, le script
			// DUStateChange...Notif ne regarde pas la fault alors qu'il le
			// faudrait...

			// 2013-03-04 18:08:52 INFO
			// JmsClientForDUStateChangeCompleteNotification: - Received
			// message:
			// id:530006;model:Device-HomeAPI;serial:AZERTY_CHANGEDUSTATE_NBBSORA;
			// params:undefined;uuid:UUID_value;deploymentUnitRef:null;version:null;
			// resolved:false;executionUnitRefList:There is no way to get this
			// info.;startTime:2013-03-04T18:06:17Z;completeTime:2013-03-04T18:06:17Z;
			// currentState:Installed;
			// fault:com.netopia.nbbs.tr69.msg.DeploymentUnitFaultStruct@292459b9;
			// result.faultCode:UNKNOWN_DEPLOYMENT_UNIT;
			// result.faultString:Unknown Deployment Unit: Update of bundle
			// (uuid: UUID_value): failed. The targeted bundle is unknown by the
			// system.;
			// extra-comment:DUStateChangeCompleted message indicates successful
			// install, or update.;
			// msg.getCommandKey():530006_TODO DEFINE THE COMMANDKEY!_UPDATE

			// -----
			// uninstall qui fonctionne
			//
			// 2013-03-04 18:04:42 INFO
			// JmsClientForDUStateChangeCompleteNotification: - Received
			// message:
			// id:530006;model:Device-HomeAPI;serial:AZERTY_CHANGEDUSTATE_NBBSORA;
			// params:undefined;uuid:UUID_value;deploymentUnitRef:32;version:2.2.0;
			// resolved:false;executionUnitRefList:There is no way to get this
			// info.;startTime:2013-03-04T18:04:27Z;completeTime:2013-03-04T18:04:27Z;
			// currentState:Uninstalled;
			// fault:com.netopia.nbbs.tr69.msg.DeploymentUnitFaultStruct@38f0411d;
			// result.faultCode:NO_FAULT;result.faultString:null;
			// extra-comment:DUStateChangeCompleted message indicates successful
			// uninstall.;
			// msg.getCommandKey():530006_TODO DEFINE THE COMMANDKEY!_UNINSTALL

			// -----
			// uninstall qui ne fonctionne pas
			//

			// TODO AAA: pb. quand une uninstall échoue, TR69/157 dit
			// currentState:Installed... Du coup, le script
			// DUStateChange...Notif ne regarde pas la fault alors qu'il le
			// faudrait...

			// 2013-03-04 18:05:12 INFO
			// JmsClientForDUStateChangeCompleteNotification: - Received
			// message:
			// id:530006;model:Device-HomeAPI;serial:AZERTY_CHANGEDUSTATE_NBBSORA;
			// params:undefined;uuid:UUID_value;deploymentUnitRef:null;version:null;
			// resolved:false;executionUnitRefList:There is no way to get this
			// info.;startTime:2013-03-04T18:04:57Z;completeTime:2013-03-04T18:04:57Z;
			// currentState:Installed;
			// fault:com.netopia.nbbs.tr69.msg.DeploymentUnitFaultStruct@2029bc2a;
			// result.faultCode:UNKNOWN_DEPLOYMENT_UNIT;
			// result.faultString:Unknown Deployment Unit: Uninstall of bundle
			// (uuid: UUID_value ): failed. The targeted bundle is unknown.;
			// extra-comment:DUStateChangeCompleted message indicates successful
			// install, or update.;
			// msg.getCommandKey():530006_TODO DEFINE THE COMMANDKEY!_UNINSTALL

			logger.info("Received message: " + textMessage.getText());
		}
		connection.close();
		logger.debug("connection is closed");
	}
}
