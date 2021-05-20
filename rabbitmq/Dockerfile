#FROM rabbitmq:3-management
#
#ADD ./configs/rabbitmq.conf /etc/rabbitmq/
#ADD ./configs/definitions.json /etc/rabbitmq/definitions.json
#
#RUN rabbitmqctl export_definitions /etc/rabbitmq/definitions.json
#
#
FROM rabbitmq:3.8.9-management

ADD ./configs/rabbitmq.conf /etc/rabbitmq/
ADD --chown=rabbitmq ./configs/definitions.json /etc/rabbitmq/

RUN apt-get update
RUN apt-get install -y curl
RUN curl -L https://github.com/rabbitmq/rabbitmq-delayed-message-exchange/releases/download/3.8.9/rabbitmq_delayed_message_exchange-3.8.9-0199d11c.ez > $RABBITMQ_HOME/plugins/rabbitmq_delayed_message_exchange-3.8.9-0199d11c.ez
RUN chown rabbitmq:rabbitmq $RABBITMQ_HOME/plugins/rabbitmq_delayed_message_exchange-3.8.9-0199d11c.ez

#Enabling pugins
RUN rabbitmq-plugins enable --offline rabbitmq_delayed_message_exchange
RUN rabbitmq-plugins enable --offline rabbitmq_consistent_hash_exchange
RUN rabbitmq-plugins enable --offline rabbitmq_management

ENV RABBITMQ_SERVER_ADDITIONAL_ERL_ARGS="-rabbitmq_management load_definitions \"/etc/rabbitmq/definitions.json\""