package com.example.prj1be.mapper;

import com.example.prj1be.domain.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("""
    INSERT INTO comment (boardId, comment, memberId)
    VALUES (#{boardId}, #{comment}, #{memberId})
    """)
    int insert(Comment comment);

    @Select("""
    SELECT c.id, c.boardId, c.memberId, m.nickName, c.comment, c.inserted
    FROM comment c JOIN member m ON c.memberId = m.id
    WHERE c.boardId = #{boardId}
    """)
    List<Comment> selectByBoardId(Integer boardId);

    @Delete("""
    DELETE FROM comment
    WHERE id = #{id}
    """)
    int deleteById(Integer id);

    @Select("""
    SELECT * FROM comment
    WHERE id = #{id}
    """)
    Comment selectById(Integer id);

    @Update("""
    UPDATE comment
    SET comment=#{comment}
    WHERE id = #{id}
    """)
    int update(Comment comment);

    @Delete("""
    DELETE FROM comment
    WHERE boardId = #{boardId}
    """)
    int deleteByBoardId(Integer boardId);

    @Delete("""
    DELETE FROM comment
    WHERE memberId = #{memberId}
    """)
    int deleteByMemberId(String memberId);
}
