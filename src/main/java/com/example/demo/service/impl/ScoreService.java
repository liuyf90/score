package com.example.demo.service.impl;

import com.example.demo.Tools;
import com.example.demo.dao.ScoreRepository;
import com.example.demo.entity.RuleEnum;
import com.example.demo.entity.Score;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.service.IScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
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

    @Override
    public double scoreByTaskofUser(Task task,User user) {
        List<Score> scoreList=task.getScores();

        double score=0.0;
        for(Score s:scoreList){
            if(s.getUser().getId()==user.getId()) {
                score += s.getSocre();
            }
        }
        return score;
    }
    @Override
    public  List<Score> scoreDetailsTask(Task task) {
        List<Score> scoreList=task.getScores();
        return scoreList;
    }

    @Override
    public  List<Score> scoreDetailsTaskofUser(Task task,User user) {
        List<Score> scoreList=task.getScores();
        Iterator<Score> it= scoreList.iterator();
        while(it.hasNext()){
            if(it.next().getUser().getId()!=user.getId()) {
                it.remove();
            }
        }
        return scoreList;
    }

    /**
     * 工时积分
     * @param user
     * @param task
     * @throws Exception
     */
    public void workTimeScore(User user, Task task) throws Exception{
        Score s = new Score();
        s.setRule("工时积分");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        double score= Tools.dateDiff(sf.parse(sf.format(task.getbDate())),sf.parse(sf.format(task.geteDate())));
        s.setSocre(score);
        s.setUser(user);
        s.setTask(task);
        scoreRepository.save(s);
    }

    /**
     * 工作超时扣分
     * @param user
     * @param task
     * @throws Exception
     */
    public void workTimeoutScore(User user,Task task) throws Exception {
        if (task.geteDate().before(task.getfDate())) {
            double score = 0 - (Tools.dateDiff(task.getfDate(), task.geteDate()));
            Score s = new Score();
            s.setRule("超时扣分");
            s.setSocre(score);
            s.setUser(user);
            s.setTask(task);
            scoreRepository.save(s);
        }
    }
        /**
         *
         * 审核超时扣分
         * @param user
         * @param task
         * @throws Exception
         */
    public void checkTimeoutScore(User user,Task task) throws Exception{
        if(Tools.dateDiff(task.getCheckDate(), task.getfDate())>1) {
            int days=(Tools.dateDiff(task.getfDate(), task.geteDate()));
            double score=0-(days*(RuleEnum.CHECK.getScore()));
            Score s = new Score();
            s.setRule("审核超时扣分");
            s.setSocre(score);
            s.setUser(user);
            s.setTask(task);
            scoreRepository.save(s);
        }


    }
}
