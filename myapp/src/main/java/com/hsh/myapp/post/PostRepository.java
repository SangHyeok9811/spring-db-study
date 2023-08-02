package com.hsh.myapp.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// JpaRepository<Contact, String>
// <엔티티클래스, 엔티티의키타입>
public interface PostRepository extends JpaRepository<Post, Long> {
}
