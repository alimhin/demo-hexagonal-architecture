package com.alimhin.sample.hexagonalarchitecture.application.port.output;

import com.alimhin.sample.hexagonalarchitecture.domain.event.CreatedArticleEvent;
import com.alimhin.sample.hexagonalarchitecture.domain.event.DraftArticleEvent;
import com.alimhin.sample.hexagonalarchitecture.domain.event.PublishedArticleEvent;
import com.alimhin.sample.hexagonalarchitecture.domain.event.UpdatedArticleEvent;

/**
 * The interface Article notification.
 */
public interface NotifyArticleEvent {

    /**
     * Notify article created.
     *
     * @param event the event
     */
    void notifyArticleCreated(CreatedArticleEvent event);

    /**
     * Notify article updated.
     *
     * @param event the event
     */
    void notifyArticleUpdated(UpdatedArticleEvent event);

    /**
     * Notify article published.
     *
     * @param event the event
     */
    void notifyArticlePublished(PublishedArticleEvent event);

    /**
     * Notify article draft.
     *
     * @param event the event
     */
    void notifyArticleDraft(DraftArticleEvent event);

}
