package com.soundaddiction.model;

import com.soundaddiction.exceptions.InvalidCommentDataException;
import com.soundaddiction.util.Checker;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class Comment {

    //Fields
    private int commentId;
    private String content;
    private User user;
    private Song song;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTimeOfComment;

    //Contructors
    public Comment(int commentId, String content, User user, Song song, LocalDateTime dateTimeOfComment)
                                                                        throws InvalidCommentDataException {

        this(content, user, song);
        this.setCommentId(commentId);
        this.setDateTimeOfComment(dateTimeOfComment);
    }

    public Comment(String content, User user, Song song) throws InvalidCommentDataException {

        this.setContent(content);
        this.setUser(user);
        this.setSong(song);
    }

    //Setters
    public void setCommentId(int commentId) throws InvalidCommentDataException {
        if(commentId > 0) {
            this.commentId = commentId;
            return;
        }
        throw new InvalidCommentDataException("Problem setting comment Id!");
    }

    public void setContent(String content) throws InvalidCommentDataException {
        if(Checker.isNotNullOrEmpty(content)) {
            this.content = content;
            return;
        }
        throw new InvalidCommentDataException("Ivalid comment's content!");
    }

    public void setUser(User user) throws InvalidCommentDataException {
        if(user != null){
            this.user = user;
            return;
        }
        throw new InvalidCommentDataException("Invalid comment's User!");
    }

    public void setSong(Song song) throws InvalidCommentDataException {
        if(song != null){
            this.song = song;
            return;
        }
        throw new InvalidCommentDataException("Invalid comment's Song!");
    }

    public void setDateTimeOfComment(LocalDateTime dateTimeOfComment) throws InvalidCommentDataException {
        if(Checker.isValidDate(dateTimeOfComment.toLocalDate())) {
            this.dateTimeOfComment = dateTimeOfComment;
            return;
        }
        throw new InvalidCommentDataException("Invalid date and time of comment setting!");
    }

    //Getters
    public int getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public Song getSong() {
        return song;
    }

    public LocalDateTime getDateTimeOfComment() {
        return dateTimeOfComment;
    }
}
