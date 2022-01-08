package com.vinaylogics.ipldashboard.data;

import com.vinaylogics.ipldashboard.models.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
@RequiredArgsConstructor
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private final EntityManager em;

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
            Map<String, Team> teamData = new HashMap<>();
            em.createQuery("SELECT DISTINCT m.team1, COUNT(m) FROM Match m GROUP BY m.team1", Object[].class)
                    .getResultList()
                    .stream()
                    .map(e -> new Team((String) e[0], (Long) e[1]))
                    .forEach(team -> teamData.put(team.getTeamName(), team));

            em.createQuery("SELECT DISTINCT m.team2, COUNT(m) FROM Match m GROUP BY m.team2", Object[].class)
                    .getResultList()
                    .forEach(e -> {
                        Team team = teamData.get((String) e[0]);
                        team.setTotalMatches(team.getTotalMatches() + (Long) e[1]);
                    });
            em.createQuery("SELECT m.matchWinner, COUNT(m) FROM Match m GROUP BY m.matchWinner", Object[].class)
                    .getResultList()
                    .forEach(e ->{
                        Team team = teamData.get((String) e[0]);
                        if(team!= null)
                        team.setTotalWins((Long) e[1]);
                    });
            teamData.values().forEach(em::persist);
            teamData.values().forEach(e -> log.info(String.valueOf(e)));
        }
    }
}
