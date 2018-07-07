package com.soundaddiction.model.dao;

import com.soundaddiction.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentDAO {

    @Autowired
    private DBManager dbManager;

    public List<Comment> getCommentsBySongId(int songId) throws SQLException {

        String commentsBySongId = "SELECT shc.comment_id, shc.user_id, shc.song_id, shc.content, shc.date_time " +
                                    "FROM song_has_comments AS shc" +
                                    "WHERE shc.song_id = 2" +
                                    "ORDER BY shc.date_time ASC;";

        List<Comment> comments =  new ArrayList<>();

        try(PreparedStatement ps = dbManager.getConnection().prepareStatement(commentsBySongId)){

        }

        return comments;
    }

}
