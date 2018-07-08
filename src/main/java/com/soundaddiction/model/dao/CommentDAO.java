package com.soundaddiction.model.dao;

import com.soundaddiction.exceptions.InvalidCommentDataException;
import com.soundaddiction.exceptions.InvalidSongDataException;
import com.soundaddiction.exceptions.InvalidUserDataException;
import com.soundaddiction.model.Comment;
import com.soundaddiction.model.Song;
import com.soundaddiction.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentDAO {

    @Autowired
    private DBManager dbManager;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private SongDAO songDAO;

    public List<Comment> getCommentsBySongId(int songId) throws SQLException, InvalidCommentDataException,
                                                        InvalidSongDataException, InvalidUserDataException {

        String commentsBySongId = "SELECT shc.comment_id, shc.user_id, shc.song_id, shc.content, shc.date_time " +
                                    "FROM song_has_comments AS shc" +
                                    " WHERE shc.song_id = ?" +
                                    " ORDER BY shc.date_time ASC;";

        List<Comment> comments =  new ArrayList<>();

        try(PreparedStatement ps = dbManager.getConnection().prepareStatement(commentsBySongId)){

            ps.setInt(1,songId);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){

                    User user = userDAO.getUserById(rs.getInt("user_id"));
                    Song song = songDAO.getSongById(songId);

                    Comment comment = new Comment(rs.getInt("comment_id"),
                                                  rs.getString("content"),
                                                  user,
                                                  song,
                                                  rs.getTimestamp("date_time").toLocalDateTime());

                    comments.add(comment);
                }
            }
        }
        return comments;
    }

}
