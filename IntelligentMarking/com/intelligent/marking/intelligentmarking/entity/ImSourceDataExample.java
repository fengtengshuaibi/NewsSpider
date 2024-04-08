package com.intelligent.marking.intelligentmarking.entity;

import java.util.ArrayList;
import java.util.List;

public class ImSourceDataExample {
    /**
     * @mbg.generated generated automatically, do not modify!
     */
    protected String orderByClause;

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    protected boolean distinct;

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    protected List<Criteria> oredCriteria;

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    public ImSourceDataExample() {
        oredCriteria = new ArrayList<>();
    }

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * @mbg.generated generated automatically, do not modify!
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andAutoincreIsNull() {
            addCriterion("autoincre is null");
            return (Criteria) this;
        }

        public Criteria andAutoincreIsNotNull() {
            addCriterion("autoincre is not null");
            return (Criteria) this;
        }

        public Criteria andAutoincreEqualTo(Long value) {
            addCriterion("autoincre =", value, "autoincre");
            return (Criteria) this;
        }

        public Criteria andAutoincreNotEqualTo(Long value) {
            addCriterion("autoincre <>", value, "autoincre");
            return (Criteria) this;
        }

        public Criteria andAutoincreGreaterThan(Long value) {
            addCriterion("autoincre >", value, "autoincre");
            return (Criteria) this;
        }

        public Criteria andAutoincreGreaterThanOrEqualTo(Long value) {
            addCriterion("autoincre >=", value, "autoincre");
            return (Criteria) this;
        }

        public Criteria andAutoincreLessThan(Long value) {
            addCriterion("autoincre <", value, "autoincre");
            return (Criteria) this;
        }

        public Criteria andAutoincreLessThanOrEqualTo(Long value) {
            addCriterion("autoincre <=", value, "autoincre");
            return (Criteria) this;
        }

        public Criteria andAutoincreIn(List<Long> values) {
            addCriterion("autoincre in", values, "autoincre");
            return (Criteria) this;
        }

        public Criteria andAutoincreNotIn(List<Long> values) {
            addCriterion("autoincre not in", values, "autoincre");
            return (Criteria) this;
        }

        public Criteria andAutoincreBetween(Long value1, Long value2) {
            addCriterion("autoincre between", value1, value2, "autoincre");
            return (Criteria) this;
        }

        public Criteria andAutoincreNotBetween(Long value1, Long value2) {
            addCriterion("autoincre not between", value1, value2, "autoincre");
            return (Criteria) this;
        }

        public Criteria andFromIsNull() {
            addCriterion("`from` is null");
            return (Criteria) this;
        }

        public Criteria andFromIsNotNull() {
            addCriterion("`from` is not null");
            return (Criteria) this;
        }

        public Criteria andFromEqualTo(String value) {
            addCriterion("`from` =", value, "from");
            return (Criteria) this;
        }

        public Criteria andFromNotEqualTo(String value) {
            addCriterion("`from` <>", value, "from");
            return (Criteria) this;
        }

        public Criteria andFromGreaterThan(String value) {
            addCriterion("`from` >", value, "from");
            return (Criteria) this;
        }

        public Criteria andFromGreaterThanOrEqualTo(String value) {
            addCriterion("`from` >=", value, "from");
            return (Criteria) this;
        }

        public Criteria andFromLessThan(String value) {
            addCriterion("`from` <", value, "from");
            return (Criteria) this;
        }

        public Criteria andFromLessThanOrEqualTo(String value) {
            addCriterion("`from` <=", value, "from");
            return (Criteria) this;
        }

        public Criteria andFromLike(String value) {
            addCriterion("`from` like", value, "from");
            return (Criteria) this;
        }

        public Criteria andFromNotLike(String value) {
            addCriterion("`from` not like", value, "from");
            return (Criteria) this;
        }

        public Criteria andFromIn(List<String> values) {
            addCriterion("`from` in", values, "from");
            return (Criteria) this;
        }

        public Criteria andFromNotIn(List<String> values) {
            addCriterion("`from` not in", values, "from");
            return (Criteria) this;
        }

        public Criteria andFromBetween(String value1, String value2) {
            addCriterion("`from` between", value1, value2, "from");
            return (Criteria) this;
        }

        public Criteria andFromNotBetween(String value1, String value2) {
            addCriterion("`from` not between", value1, value2, "from");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andLinkIsNull() {
            addCriterion("link is null");
            return (Criteria) this;
        }

        public Criteria andLinkIsNotNull() {
            addCriterion("link is not null");
            return (Criteria) this;
        }

        public Criteria andLinkEqualTo(String value) {
            addCriterion("link =", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotEqualTo(String value) {
            addCriterion("link <>", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkGreaterThan(String value) {
            addCriterion("link >", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkGreaterThanOrEqualTo(String value) {
            addCriterion("link >=", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkLessThan(String value) {
            addCriterion("link <", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkLessThanOrEqualTo(String value) {
            addCriterion("link <=", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkLike(String value) {
            addCriterion("link like", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotLike(String value) {
            addCriterion("link not like", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkIn(List<String> values) {
            addCriterion("link in", values, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotIn(List<String> values) {
            addCriterion("link not in", values, "link");
            return (Criteria) this;
        }

        public Criteria andLinkBetween(String value1, String value2) {
            addCriterion("link between", value1, value2, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotBetween(String value1, String value2) {
            addCriterion("link not between", value1, value2, "link");
            return (Criteria) this;
        }

        public Criteria andTagIsNull() {
            addCriterion("tag is null");
            return (Criteria) this;
        }

        public Criteria andTagIsNotNull() {
            addCriterion("tag is not null");
            return (Criteria) this;
        }

        public Criteria andTagEqualTo(String value) {
            addCriterion("tag =", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotEqualTo(String value) {
            addCriterion("tag <>", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagGreaterThan(String value) {
            addCriterion("tag >", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagGreaterThanOrEqualTo(String value) {
            addCriterion("tag >=", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLessThan(String value) {
            addCriterion("tag <", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLessThanOrEqualTo(String value) {
            addCriterion("tag <=", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLike(String value) {
            addCriterion("tag like", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotLike(String value) {
            addCriterion("tag not like", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagIn(List<String> values) {
            addCriterion("tag in", values, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotIn(List<String> values) {
            addCriterion("tag not in", values, "tag");
            return (Criteria) this;
        }

        public Criteria andTagBetween(String value1, String value2) {
            addCriterion("tag between", value1, value2, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotBetween(String value1, String value2) {
            addCriterion("tag not between", value1, value2, "tag");
            return (Criteria) this;
        }

        public Criteria andReadingamountIsNull() {
            addCriterion("readingamount is null");
            return (Criteria) this;
        }

        public Criteria andReadingamountIsNotNull() {
            addCriterion("readingamount is not null");
            return (Criteria) this;
        }

        public Criteria andReadingamountEqualTo(Integer value) {
            addCriterion("readingamount =", value, "readingamount");
            return (Criteria) this;
        }

        public Criteria andReadingamountNotEqualTo(Integer value) {
            addCriterion("readingamount <>", value, "readingamount");
            return (Criteria) this;
        }

        public Criteria andReadingamountGreaterThan(Integer value) {
            addCriterion("readingamount >", value, "readingamount");
            return (Criteria) this;
        }

        public Criteria andReadingamountGreaterThanOrEqualTo(Integer value) {
            addCriterion("readingamount >=", value, "readingamount");
            return (Criteria) this;
        }

        public Criteria andReadingamountLessThan(Integer value) {
            addCriterion("readingamount <", value, "readingamount");
            return (Criteria) this;
        }

        public Criteria andReadingamountLessThanOrEqualTo(Integer value) {
            addCriterion("readingamount <=", value, "readingamount");
            return (Criteria) this;
        }

        public Criteria andReadingamountIn(List<Integer> values) {
            addCriterion("readingamount in", values, "readingamount");
            return (Criteria) this;
        }

        public Criteria andReadingamountNotIn(List<Integer> values) {
            addCriterion("readingamount not in", values, "readingamount");
            return (Criteria) this;
        }

        public Criteria andReadingamountBetween(Integer value1, Integer value2) {
            addCriterion("readingamount between", value1, value2, "readingamount");
            return (Criteria) this;
        }

        public Criteria andReadingamountNotBetween(Integer value1, Integer value2) {
            addCriterion("readingamount not between", value1, value2, "readingamount");
            return (Criteria) this;
        }

        public Criteria andCommentamountIsNull() {
            addCriterion("commentamount is null");
            return (Criteria) this;
        }

        public Criteria andCommentamountIsNotNull() {
            addCriterion("commentamount is not null");
            return (Criteria) this;
        }

        public Criteria andCommentamountEqualTo(Integer value) {
            addCriterion("commentamount =", value, "commentamount");
            return (Criteria) this;
        }

        public Criteria andCommentamountNotEqualTo(Integer value) {
            addCriterion("commentamount <>", value, "commentamount");
            return (Criteria) this;
        }

        public Criteria andCommentamountGreaterThan(Integer value) {
            addCriterion("commentamount >", value, "commentamount");
            return (Criteria) this;
        }

        public Criteria andCommentamountGreaterThanOrEqualTo(Integer value) {
            addCriterion("commentamount >=", value, "commentamount");
            return (Criteria) this;
        }

        public Criteria andCommentamountLessThan(Integer value) {
            addCriterion("commentamount <", value, "commentamount");
            return (Criteria) this;
        }

        public Criteria andCommentamountLessThanOrEqualTo(Integer value) {
            addCriterion("commentamount <=", value, "commentamount");
            return (Criteria) this;
        }

        public Criteria andCommentamountIn(List<Integer> values) {
            addCriterion("commentamount in", values, "commentamount");
            return (Criteria) this;
        }

        public Criteria andCommentamountNotIn(List<Integer> values) {
            addCriterion("commentamount not in", values, "commentamount");
            return (Criteria) this;
        }

        public Criteria andCommentamountBetween(Integer value1, Integer value2) {
            addCriterion("commentamount between", value1, value2, "commentamount");
            return (Criteria) this;
        }

        public Criteria andCommentamountNotBetween(Integer value1, Integer value2) {
            addCriterion("commentamount not between", value1, value2, "commentamount");
            return (Criteria) this;
        }

        public Criteria andLikesIsNull() {
            addCriterion("likes is null");
            return (Criteria) this;
        }

        public Criteria andLikesIsNotNull() {
            addCriterion("likes is not null");
            return (Criteria) this;
        }

        public Criteria andLikesEqualTo(Integer value) {
            addCriterion("likes =", value, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesNotEqualTo(Integer value) {
            addCriterion("likes <>", value, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesGreaterThan(Integer value) {
            addCriterion("likes >", value, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesGreaterThanOrEqualTo(Integer value) {
            addCriterion("likes >=", value, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesLessThan(Integer value) {
            addCriterion("likes <", value, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesLessThanOrEqualTo(Integer value) {
            addCriterion("likes <=", value, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesIn(List<Integer> values) {
            addCriterion("likes in", values, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesNotIn(List<Integer> values) {
            addCriterion("likes not in", values, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesBetween(Integer value1, Integer value2) {
            addCriterion("likes between", value1, value2, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesNotBetween(Integer value1, Integer value2) {
            addCriterion("likes not between", value1, value2, "likes");
            return (Criteria) this;
        }

        public Criteria andBelongsIsNull() {
            addCriterion("belongs is null");
            return (Criteria) this;
        }

        public Criteria andBelongsIsNotNull() {
            addCriterion("belongs is not null");
            return (Criteria) this;
        }

        public Criteria andBelongsEqualTo(String value) {
            addCriterion("belongs =", value, "belongs");
            return (Criteria) this;
        }

        public Criteria andBelongsNotEqualTo(String value) {
            addCriterion("belongs <>", value, "belongs");
            return (Criteria) this;
        }

        public Criteria andBelongsGreaterThan(String value) {
            addCriterion("belongs >", value, "belongs");
            return (Criteria) this;
        }

        public Criteria andBelongsGreaterThanOrEqualTo(String value) {
            addCriterion("belongs >=", value, "belongs");
            return (Criteria) this;
        }

        public Criteria andBelongsLessThan(String value) {
            addCriterion("belongs <", value, "belongs");
            return (Criteria) this;
        }

        public Criteria andBelongsLessThanOrEqualTo(String value) {
            addCriterion("belongs <=", value, "belongs");
            return (Criteria) this;
        }

        public Criteria andBelongsLike(String value) {
            addCriterion("belongs like", value, "belongs");
            return (Criteria) this;
        }

        public Criteria andBelongsNotLike(String value) {
            addCriterion("belongs not like", value, "belongs");
            return (Criteria) this;
        }

        public Criteria andBelongsIn(List<String> values) {
            addCriterion("belongs in", values, "belongs");
            return (Criteria) this;
        }

        public Criteria andBelongsNotIn(List<String> values) {
            addCriterion("belongs not in", values, "belongs");
            return (Criteria) this;
        }

        public Criteria andBelongsBetween(String value1, String value2) {
            addCriterion("belongs between", value1, value2, "belongs");
            return (Criteria) this;
        }

        public Criteria andBelongsNotBetween(String value1, String value2) {
            addCriterion("belongs not between", value1, value2, "belongs");
            return (Criteria) this;
        }

        public Criteria andArticleidIsNull() {
            addCriterion("articleid is null");
            return (Criteria) this;
        }

        public Criteria andArticleidIsNotNull() {
            addCriterion("articleid is not null");
            return (Criteria) this;
        }

        public Criteria andArticleidEqualTo(String value) {
            addCriterion("articleid =", value, "articleid");
            return (Criteria) this;
        }

        public Criteria andArticleidNotEqualTo(String value) {
            addCriterion("articleid <>", value, "articleid");
            return (Criteria) this;
        }

        public Criteria andArticleidGreaterThan(String value) {
            addCriterion("articleid >", value, "articleid");
            return (Criteria) this;
        }

        public Criteria andArticleidGreaterThanOrEqualTo(String value) {
            addCriterion("articleid >=", value, "articleid");
            return (Criteria) this;
        }

        public Criteria andArticleidLessThan(String value) {
            addCriterion("articleid <", value, "articleid");
            return (Criteria) this;
        }

        public Criteria andArticleidLessThanOrEqualTo(String value) {
            addCriterion("articleid <=", value, "articleid");
            return (Criteria) this;
        }

        public Criteria andArticleidLike(String value) {
            addCriterion("articleid like", value, "articleid");
            return (Criteria) this;
        }

        public Criteria andArticleidNotLike(String value) {
            addCriterion("articleid not like", value, "articleid");
            return (Criteria) this;
        }

        public Criteria andArticleidIn(List<String> values) {
            addCriterion("articleid in", values, "articleid");
            return (Criteria) this;
        }

        public Criteria andArticleidNotIn(List<String> values) {
            addCriterion("articleid not in", values, "articleid");
            return (Criteria) this;
        }

        public Criteria andArticleidBetween(String value1, String value2) {
            addCriterion("articleid between", value1, value2, "articleid");
            return (Criteria) this;
        }

        public Criteria andArticleidNotBetween(String value1, String value2) {
            addCriterion("articleid not between", value1, value2, "articleid");
            return (Criteria) this;
        }

        public Criteria andSubmittimeIsNull() {
            addCriterion("submitTime is null");
            return (Criteria) this;
        }

        public Criteria andSubmittimeIsNotNull() {
            addCriterion("submitTime is not null");
            return (Criteria) this;
        }

        public Criteria andSubmittimeEqualTo(String value) {
            addCriterion("submitTime =", value, "submittime");
            return (Criteria) this;
        }

        public Criteria andSubmittimeNotEqualTo(String value) {
            addCriterion("submitTime <>", value, "submittime");
            return (Criteria) this;
        }

        public Criteria andSubmittimeGreaterThan(String value) {
            addCriterion("submitTime >", value, "submittime");
            return (Criteria) this;
        }

        public Criteria andSubmittimeGreaterThanOrEqualTo(String value) {
            addCriterion("submitTime >=", value, "submittime");
            return (Criteria) this;
        }

        public Criteria andSubmittimeLessThan(String value) {
            addCriterion("submitTime <", value, "submittime");
            return (Criteria) this;
        }

        public Criteria andSubmittimeLessThanOrEqualTo(String value) {
            addCriterion("submitTime <=", value, "submittime");
            return (Criteria) this;
        }

        public Criteria andSubmittimeLike(String value) {
            addCriterion("submitTime like", value, "submittime");
            return (Criteria) this;
        }

        public Criteria andSubmittimeNotLike(String value) {
            addCriterion("submitTime not like", value, "submittime");
            return (Criteria) this;
        }

        public Criteria andSubmittimeIn(List<String> values) {
            addCriterion("submitTime in", values, "submittime");
            return (Criteria) this;
        }

        public Criteria andSubmittimeNotIn(List<String> values) {
            addCriterion("submitTime not in", values, "submittime");
            return (Criteria) this;
        }

        public Criteria andSubmittimeBetween(String value1, String value2) {
            addCriterion("submitTime between", value1, value2, "submittime");
            return (Criteria) this;
        }

        public Criteria andSubmittimeNotBetween(String value1, String value2) {
            addCriterion("submitTime not between", value1, value2, "submittime");
            return (Criteria) this;
        }

        public Criteria andKeywordsIsNull() {
            addCriterion("keywords is null");
            return (Criteria) this;
        }

        public Criteria andKeywordsIsNotNull() {
            addCriterion("keywords is not null");
            return (Criteria) this;
        }

        public Criteria andKeywordsEqualTo(String value) {
            addCriterion("keywords =", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotEqualTo(String value) {
            addCriterion("keywords <>", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsGreaterThan(String value) {
            addCriterion("keywords >", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsGreaterThanOrEqualTo(String value) {
            addCriterion("keywords >=", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLessThan(String value) {
            addCriterion("keywords <", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLessThanOrEqualTo(String value) {
            addCriterion("keywords <=", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLike(String value) {
            addCriterion("keywords like", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotLike(String value) {
            addCriterion("keywords not like", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsIn(List<String> values) {
            addCriterion("keywords in", values, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotIn(List<String> values) {
            addCriterion("keywords not in", values, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsBetween(String value1, String value2) {
            addCriterion("keywords between", value1, value2, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotBetween(String value1, String value2) {
            addCriterion("keywords not between", value1, value2, "keywords");
            return (Criteria) this;
        }

        public Criteria andRankIsNull() {
            addCriterion("`rank` is null");
            return (Criteria) this;
        }

        public Criteria andRankIsNotNull() {
            addCriterion("`rank` is not null");
            return (Criteria) this;
        }

        public Criteria andRankEqualTo(Integer value) {
            addCriterion("`rank` =", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotEqualTo(Integer value) {
            addCriterion("`rank` <>", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankGreaterThan(Integer value) {
            addCriterion("`rank` >", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankGreaterThanOrEqualTo(Integer value) {
            addCriterion("`rank` >=", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankLessThan(Integer value) {
            addCriterion("`rank` <", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankLessThanOrEqualTo(Integer value) {
            addCriterion("`rank` <=", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankIn(List<Integer> values) {
            addCriterion("`rank` in", values, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotIn(List<Integer> values) {
            addCriterion("`rank` not in", values, "rank");
            return (Criteria) this;
        }

        public Criteria andRankBetween(Integer value1, Integer value2) {
            addCriterion("`rank` between", value1, value2, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotBetween(Integer value1, Integer value2) {
            addCriterion("`rank` not between", value1, value2, "rank");
            return (Criteria) this;
        }

        public Criteria andRecordtimeIsNull() {
            addCriterion("recordTime is null");
            return (Criteria) this;
        }

        public Criteria andRecordtimeIsNotNull() {
            addCriterion("recordTime is not null");
            return (Criteria) this;
        }

        public Criteria andRecordtimeEqualTo(String value) {
            addCriterion("recordTime =", value, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeNotEqualTo(String value) {
            addCriterion("recordTime <>", value, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeGreaterThan(String value) {
            addCriterion("recordTime >", value, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeGreaterThanOrEqualTo(String value) {
            addCriterion("recordTime >=", value, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeLessThan(String value) {
            addCriterion("recordTime <", value, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeLessThanOrEqualTo(String value) {
            addCriterion("recordTime <=", value, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeLike(String value) {
            addCriterion("recordTime like", value, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeNotLike(String value) {
            addCriterion("recordTime not like", value, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeIn(List<String> values) {
            addCriterion("recordTime in", values, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeNotIn(List<String> values) {
            addCriterion("recordTime not in", values, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeBetween(String value1, String value2) {
            addCriterion("recordTime between", value1, value2, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeNotBetween(String value1, String value2) {
            addCriterion("recordTime not between", value1, value2, "recordtime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}