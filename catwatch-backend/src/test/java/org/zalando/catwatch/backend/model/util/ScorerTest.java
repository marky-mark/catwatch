package org.zalando.catwatch.backend.model.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.zalando.catwatch.backend.model.Project;
import org.zalando.catwatch.backend.model.util.Scorer;
import org.zalando.catwatch.backend.repo.builder.ProjectBuilder;

public class ScorerTest {

    @Test
    public void testScore() throws Exception {

        // given
        Scorer scorer = new Scorer();
        scorer.setScoringProject("function(project) { return project.forksCount > 0 ? ( "
                + "project.starsCount + project.forksCount + project.contributorsCount + "
                + "project.commitsCount / 100 ) : 0 }");

        // when
        Project project = new ProjectBuilder().starsCount(20).forksCount(10).contributorsCount(5).commitsCount(230)
                .create();

        // then
        assertEquals(20 + 10 + 5 + 2, scorer.score(project));

        // when
        project.setForksCount(0);

        // then
        assertEquals(0, scorer.score(project));
    }
}
