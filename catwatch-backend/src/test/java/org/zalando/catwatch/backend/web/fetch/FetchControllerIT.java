package org.zalando.catwatch.backend.web.fetch;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.core.env.Environment;
import org.zalando.catwatch.backend.model.Contributor;
import org.zalando.catwatch.backend.model.Project;
import org.zalando.catwatch.backend.model.Statistics;
import org.zalando.catwatch.backend.repo.ContributorRepository;
import org.zalando.catwatch.backend.repo.ProjectRepository;
import org.zalando.catwatch.backend.repo.StatisticsRepository;
import org.zalando.catwatch.backend.web.AbstractCatwatchIT;

@IntegrationTest({ "github.login=", "organization.list=rwitzeltestorg,rwitzeltestorg2", "server.port=0" })
public class FetchControllerIT extends AbstractCatwatchIT {

    @Autowired
    private Environment env;

    @Autowired
    private ContributorRepository contributorRepository;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void testFetch() throws Exception {

        // given
        contributorRepository.deleteAll();
        statisticsRepository.deleteAll();
        projectRepository.deleteAll();

        // when
        String result = template.getForEntity(fetchUrl(), String.class).getBody();

        // then
        assertThat(result, equalTo("OK"));

        List<Statistics> statisticses = newArrayList(statisticsRepository.findAll());
        List<Contributor> contributors = newArrayList(contributorRepository.findAll());
        List<Project> projects = newArrayList(projectRepository.findAll());

        assertThat(statisticses.size(), equalTo(2));
        assertThat(projects.size(), equalTo(2));
        assertThat(contributors.size(), equalTo(2));

        Statistics statistics = sort(statisticses, "organizationName").get(0);
        Project project = sort(projects, "organizationName").get(0);

        assertThat(statistics.getOrganizationName(), equalTo("rwitzeltestorg"));
        assertThat(project.getName(), equalTo("testrepo1"));
        assertThat(contributors.get(0).getName(), equalTo("Rodrigo Witzel"));
        assertThat(contributors.get(1).getName(), equalTo("Rodrigo Witzel"));
    }

    private String fetchUrl() {
        return fromHttpUrl(base.toString() + "fetch").toUriString();
    }

    private <T> List<T> sort(Collection<T> collection, String property) {
        return collection.stream().sorted(new BeanComparator<T>(property)).collect(toList());
    }

}
