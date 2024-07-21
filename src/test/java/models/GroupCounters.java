package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Pojo класс тела ответа со счетчиками группы
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupCounters {
    private Integer ads_topics;
    private Integer black_list;
    private Integer catalogs;
    private Integer delayed_topics;
    private Integer friends;
    private Integer join_requests;
    private Integer links;
    private Integer maybe;
    private Integer members;
    private Integer moderators;
    private Integer music_tracks;
    private Integer new_paid_topics;
    private Integer own_products;
    private Integer paid_members;
    private Integer paid_topics;
    private Integer photo_albums;
    private Integer photos;
    private Integer pinned_topics;
    private Integer presents;
    private Integer products;
    private Integer promo_on_moderation;
    private Integer suggested_products;
    private Integer suggested_topics;
    private Integer themes;
    private Integer unpublished_topics;
    private Integer videos;
}

