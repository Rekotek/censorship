package com.scriptorium.censorship.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentParams {
    private long fileSize;
    private LocalDateTime lastModified;
    private boolean xlsx;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContentParams that = (ContentParams) o;
        return fileSize == that.fileSize &&
                lastModified == that.lastModified;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileSize, lastModified);
    }
}
