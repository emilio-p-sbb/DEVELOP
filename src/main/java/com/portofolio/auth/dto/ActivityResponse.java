package com.portofolio.auth.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ActivityResponse {

    @Builder.Default
    private List<DashboardStat> stats = new ArrayList<>();

    @Builder.Default
    private List<RecentActivity> recentActivities = new ArrayList<>();
}
