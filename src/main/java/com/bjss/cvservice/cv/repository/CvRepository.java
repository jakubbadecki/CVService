package com.bjss.cvservice.cv.repository;

import com.bjss.cvservice.cv.entity.dao.Cv;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvRepository extends MongoRepository<Cv, String> {
}
