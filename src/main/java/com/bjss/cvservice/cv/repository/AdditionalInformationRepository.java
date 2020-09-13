package com.bjss.cvservice.cv.repository;

import com.bjss.cvservice.cv.entity.dao.AdditionalInformation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdditionalInformationRepository extends MongoRepository<AdditionalInformation, String> {
}
