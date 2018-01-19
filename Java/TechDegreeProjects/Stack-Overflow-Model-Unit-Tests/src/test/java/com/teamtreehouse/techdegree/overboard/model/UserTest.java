package com.teamtreehouse.techdegree.overboard.model;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import static org.junit.Assert.*;

/**
 * Created by ronaldwilliams on 7/13/17.
 */
public class UserTest {
    private Board board;
    private User user1;
    private User user2;
    private Question question;
    private Answer answer;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        board = new Board("Java");
        user1 = board.createUser("user1");
        user2 = board.createUser("user2");
        question = user1.askQuestion("This is a question?");
        answer = user2.answerQuestion(question, "Yes, that is technically a question.");

    }

    @Test
    public void upVoteQuestion() throws Exception {
        user2.upVote(question);

        assertEquals(5, user1.getReputation());
    }

    @Test
    public void upVoteAnswer() throws Exception {
        user1.upVote(answer);

        assertEquals(10, user2.getReputation());
    }

    @Test
    public void answerAcceptedRep() throws Exception {
        user1.acceptAnswer(answer);

        assertEquals(15, user2.getReputation());
    }

    @Test(expected=RuntimeException.class)
    public void upVoteYourQuestion() throws Exception {
        user1.upVote(question);
    }

    @Test(expected=RuntimeException.class)
    public void upVoteYourAnswer() throws Exception {
        user2.upVote(answer);
    }

    @Test(expected=RuntimeException.class)
    public void whoCanAcceptAnswer() throws Exception {
        user2.acceptAnswer(answer);
    }

    //additional test of getReputation for far exceeds / extra credit
    @Test
    public void noDownVotes() throws Exception {
        user2.downVote(question);

        assertEquals(0, user1.getReputation());
    }
}