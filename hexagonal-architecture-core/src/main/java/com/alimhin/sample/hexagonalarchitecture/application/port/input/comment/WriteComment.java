package com.alimhin.sample.hexagonalarchitecture.application.port.input.comment;

import com.alimhin.sample.hexagonalarchitecture.domain.model.Comment;

/**
 * The interface Write comment.
 */
public interface WriteComment {

    /**
     * Add comment comment.
     *
     * @param articleId the article id
     * @param comment   the comment
     * @return the comment
     */
    Comment addComment(Long articleId, String comment);

    /**
     * Update comment comment.
     *
     * @param id             the id
     * @param updatedComment the updated comment
     * @return the comment
     */
    Comment updateComment(Long id, String updatedComment);

    /**
     * Delete comment boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deleteComment(Long id);
}
