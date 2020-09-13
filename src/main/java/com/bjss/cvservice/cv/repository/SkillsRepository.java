package com.bjss.cvservice.cv.repository;

import com.bjss.cvservice.cv.entity.dao.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SkillsRepository extends MongoRepository<Skill, String> {
}
