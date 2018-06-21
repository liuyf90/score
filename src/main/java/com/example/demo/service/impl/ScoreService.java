package com.example.demo.service.impl;

import com.example.demo.dao.ScoreRepository;
import com.example.demo.entity.RuleEnum;
import com.example.demo.entity.Score;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.service.IScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jnlp.IntegrationService;
import javax.persistence.Id;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by liuyf on 2018/6/20.
 */
@Service
@Transactional
public class ScoreService implements IScore{
    @Autowired
    private ScoreRepository scoreRepository;

    @Override
    public void score(User user, RuleEnum ruleEnum,Task task) throws Exception {
        try {
            Score s = new Score();
            s.setRule(ruleEnum.getRule());
            s.setSocre(ruleEnum.getScore());
            s.setUser(user);
            s.setTask(task);
            scoreRepository.save(s);
        }catch (Exception e){
            throw new Exception("save Scroe Exception");
        }
    }

    @Override
    public double scoreByUser(User user) {
       List<Score> scoreList=this.scoreRepository.findByUserId(user.getId());
       double score=0.0;
       for(Score s:scoreList){
           score+=s.getSocre();
       }
       return score;
    }
}
