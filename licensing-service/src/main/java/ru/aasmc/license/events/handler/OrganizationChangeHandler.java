package ru.aasmc.license.events.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import ru.aasmc.license.events.CustomChannels;
import ru.aasmc.license.events.model.OrganizationChangeModel;
import ru.aasmc.license.repository.OrganizationRedisRepository;

@EnableBinding(CustomChannels.class)
public class OrganizationChangeHandler {

    @Autowired
    private OrganizationRedisRepository redisRepository;

    private static final Logger logger = LoggerFactory.getLogger(OrganizationChangeHandler.class);

    @StreamListener("inboundOrgChanges")
    public void loggerSink(OrganizationChangeModel organization) {
        logger.debug("Received a message of type " + organization.getType());

        switch(organization.getAction()){
            case "GET":
                logger.debug("Received a GET event from the organization service for organization id {}", organization.getOrganizationId());
                break;
            case "SAVE":
                logger.debug("Received a SAVE event from the organization service for organization id {}", organization.getOrganizationId());
                break;
            case "UPDATE":
                logger.debug("Received a UPDATE event from the organization service for organization id {}", organization.getOrganizationId());
                redisRepository.deleteById(organization.getOrganizationId());
                break;
            case "DELETE":
                logger.debug("Received a DELETE event from the organization service for organization id {}", organization.getOrganizationId());
                redisRepository.deleteById(organization.getOrganizationId());
                break;
            default:
                logger.error("Received an UNKNOWN event from the organization service of type {}", organization.getType());
                break;
        }
    }
}
