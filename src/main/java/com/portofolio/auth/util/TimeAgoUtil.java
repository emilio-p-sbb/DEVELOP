package com.portofolio.auth.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeAgoUtil {

	public static String toTimeAgo(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(dateTime, now);

        long seconds = duration.getSeconds();

        if (seconds < 60) {
            return "just now";
        }

        long minutes = seconds / 60;
        if (minutes < 60) {
            return minutes + " minutes ago";
        }

        long hours = minutes / 60;
        if (hours < 24) {
            return hours + " hours ago";
        }

        long days = hours / 24;
        if (days < 7) {
            return days + " days ago";
        }

        long weeks = days / 7;
        if (weeks < 4) {
            return weeks + " weeks ago";
        }

        long months = days / 30;
        if (months < 12) {
            return months + " months ago";
        }

        long years = days / 365;
        return years + " years ago";
    }
	
}
