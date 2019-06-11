package com.dcorn.api.file;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IFileRepository extends JpaRepository<Image, Integer> {
}
