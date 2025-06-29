package com.portofolio.auth.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portofolio.auth.dto.ActivityResponse;
import com.portofolio.auth.dto.DashboardStat;
import com.portofolio.auth.dto.RecentActivity;
import com.portofolio.auth.model.Experience;
import com.portofolio.auth.model.Message;
import com.portofolio.auth.model.Project;
import com.portofolio.auth.model.Skill;
import com.portofolio.auth.repository.ExperienceRepository;
import com.portofolio.auth.repository.MessageRepository;
import com.portofolio.auth.repository.ProjectRepository;
import com.portofolio.auth.repository.SkillRepository;
import com.portofolio.auth.util.TimeAgoUtil;

@Service
public class HomeServiceImpl implements HomeService {
	
	@Autowired ProjectRepository projectRepository;
	@Autowired SkillRepository skillRepository;
	@Autowired MessageRepository messageRepository;
	@Autowired ExperienceRepository experienceRepository;

	@Override
	public ActivityResponse getActivities() throws JsonProcessingException {
		List<Project> projects = projectRepository.findTop3ByOrderByUpdatedAtDesc();
		List<Skill> skills = skillRepository.findTop3ByOrderByUpdatedAtDesc();
		List<Message> messages = messageRepository.findTop3ByOrderByUpdatedAtDesc();
		List<Experience> experiences = experienceRepository.findTop3ByOrderByUpdatedAtDesc();
		
		List<RecentActivity> recentActivities = new ArrayList<>();
		for (Project project : projects) {
			recentActivities.add(RecentActivity.builder()
	                .action("Create Project: "+project.getTitle())
	                .time(TimeAgoUtil.toTimeAgo(project.getUpdatedAt()))
	                .icon("Code")
	                .build());
	    }

	    // Skill
	    for (Skill skill : skills) {
	    	recentActivities.add(RecentActivity.builder()
	                .action("Create Skill: "+skill.getName())
	                .time(TimeAgoUtil.toTimeAgo(skill.getUpdatedAt()))
	                .icon("GraduationCap")
	                .build());
	    }

	    // Experience
	    for (Experience exp : experiences) {
	    	recentActivities.add(RecentActivity.builder()
	                .action("Create Experiences: "+exp.getPositionTitle())
	                .time(TimeAgoUtil.toTimeAgo(exp.getUpdatedAt()))
	                .icon("Briefcase")
	                .build());
	    }

	    // Message
	    for (Message msg : messages) {
	    	recentActivities.add(RecentActivity.builder()
	                .action("Create Messages: "+msg.getSubject())
	                .time(TimeAgoUtil.toTimeAgo(msg.getUpdatedAt()))
	                .icon("MessageCircle")
	                .build());
	    }
	    
	    LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = LocalDate.now().plusMonths(1).withDayOfMonth(1).atStartOfDay().minusSeconds(1);

        // Projects
        long totalProjects = projectRepository.count();
        long newProjects = projectRepository.countByCreatedAtBetween(startOfMonth, endOfMonth);
        DashboardStat projectStat = DashboardStat.builder()
                .title("Total Projects")
                .value(String.valueOf(totalProjects))
                .icon("Code")
                .change("↑ +" + newProjects + " this month")
                .color("bg-blue-500")
                .build();

        // Experiences
        long totalExp = experienceRepository.count();
        long newExp = experienceRepository.countByCreatedAtBetween(startOfMonth, endOfMonth);
        DashboardStat expStat = DashboardStat.builder()
                .title("Work Experience")
                .value(String.valueOf(totalExp))
                .icon("Briefcase")
                .change("↑ +" + newExp + " this month")
                .color("bg-green-500")
                .build();

        // Skills
        long totalSkills = skillRepository.count();
        long newSkills = skillRepository.countByCreatedAtBetween(startOfMonth, endOfMonth);
        DashboardStat skillStat = DashboardStat.builder()
                .title("Skills")
                .value(String.valueOf(totalSkills))
                .icon("GraduationCap")
                .change("↑ +" + newSkills + " this month")
                .color("bg-yellow-500")
                .build();

        // Portfolio Views (contoh dummy)
        long viewsThisMonth = 2400;
        long viewsLastMonth = 2100;
        double growthPercent = ((double)(viewsThisMonth - viewsLastMonth) / viewsLastMonth) * 100;
        DashboardStat viewsStat = DashboardStat.builder()
                .title("Portfolio Views")
                .value("2.4k")
                .icon("Eye")
                .change("↑ +" + String.format("%.0f", growthPercent) + "% this month")
                .color("bg-purple-500")
                .build();
        
        List<DashboardStat> dashboardStats  = List.of(projectStat, expStat, skillStat, viewsStat);
        
		return ActivityResponse.builder().recentActivities(recentActivities).stats(dashboardStats).build();
	}

}
