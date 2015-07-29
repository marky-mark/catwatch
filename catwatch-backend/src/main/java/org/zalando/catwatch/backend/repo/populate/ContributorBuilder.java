package org.zalando.catwatch.backend.repo.populate;

import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.zalando.catwatch.backend.repo.populate.BuilderUtil.freshId;
import static org.zalando.catwatch.backend.repo.populate.BuilderUtil.random;
import static org.zalando.catwatch.backend.repo.populate.BuilderUtil.randomDate;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.zalando.catwatch.backend.model.Contributor;
import org.zalando.catwatch.backend.repo.ContributorRepository;

public class ContributorBuilder {

    private static Instant now = now();

    private Contributor contributor;

    private ContributorRepository contributorRepository;

    public ContributorBuilder(ContributorRepository contributorRepository) {
        this.contributorRepository = contributorRepository;
        contributor = new Contributor(freshId(), freshId(), randomDate());
        contributor.setName("" + UUID.randomUUID());
        contributor.setUrl("https://github.com/" + contributor.getName());
        contributor.setOrganizationName("" + UUID.randomUUID());
        contributor.setOrganizationalCommitsCount(50 + random(1, 40));
        contributor.setOrganizationalProjectsCount(5 + random(1, 4));
        contributor.setPersonalCommitsCount(random(1, 40));
        contributor.setPersonalProjectsCount(random(1, 4));
    }

    public ContributorBuilder id(long id) {
        contributor.getKey().setId(id);
        return this;
    }

    public ContributorBuilder name(String name) {
        contributor.setName(name);
        return this;
    }

    public ContributorBuilder days(int numDaysBeforeNow) {
        contributor.getKey().setSnapshotDate(Date.from(now.minus(numDaysBeforeNow, DAYS)));
        return this;
    }

    public ContributorBuilder organizationName(String organizationName) {
        contributor.setOrganizationName(organizationName);
        return this;
    }

    public ContributorBuilder organizationId(long organizationId) {
        contributor.getKey().setOrganizationId(organizationId);
        return this;
    }

    public ContributorBuilder orgCommits(Integer organizationalCommitsCount) {
        contributor.setOrganizationalCommitsCount(organizationalCommitsCount);
        return this;
    }

    public Contributor create() {
        Contributor c = new Contributor(contributor.getId(), contributor.getOrganizationId(),
                contributor.getSnapshotDate());
        c.setName(contributor.getName());
        c.setUrl(contributor.getUrl());
        c.setOrganizationName(contributor.getOrganizationName());
        c.setOrganizationalCommitsCount(contributor.getOrganizationalCommitsCount());
        c.setOrganizationalProjectsCount(contributor.getOrganizationalProjectsCount());
        c.setPersonalCommitsCount(contributor.getPersonalCommitsCount());
        c.setPersonalProjectsCount(contributor.getPersonalProjectsCount());
        return c;
    }

    public Contributor save() {
        return contributorRepository.save(create());
    }

}
