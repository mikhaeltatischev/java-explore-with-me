package ru.practicum.explorewithme.compilation.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.compilation.model.Compilation;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    List<Compilation> getCompilationByPinned(boolean pinned, PageRequest pageRequest);

    List<Compilation> getCompilationBy(PageRequest pageRequest);
}