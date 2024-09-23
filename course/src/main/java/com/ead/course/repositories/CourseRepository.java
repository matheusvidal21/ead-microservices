package com.ead.course.repositories;

import com.ead.course.models.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseModel, UUID>, JpaSpecificationExecutor<CourseModel> {

    @Query(nativeQuery = true, value = "SELECT CASE WHEN COUNT(TCU) > 0 THEN true ELSE false END FROM tb_courses_users tcu WHERE tcu.course_id = :courseId AND tcu.user_id = :userId")
    boolean existsByCourseAndUser(@Param("courseId") UUID courseId, @Param("userId") UUID userId);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO tb_courses_users VALUES (:courseId, :userId)")
    void saveSubscriptionUserInCourse(@Param("courseId") UUID courseId, @Param("userId") UUID userId);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM tb_courses_users WHERE user_id = :userId")
    void deleteCourseUserByUser(@Param("userId") UUID userId);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM tb_courses_users WHERE course_id = :courseId")
    void deleteCourseUserByCourse(@Param("courseId") UUID courseId);

}
