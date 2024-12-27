package com.locationproject.projectlocation.GoogleMap.GoogleMapDto;
import java.util.List;

public class GoogleDistanceMatrixResponse {
    private List<Row> rows;
    private String status;

    // Getters and setters
    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Inner class for Row
    public static class Row {
        private List<GoogleDistanceMatrixElement> elements;

        public List<GoogleDistanceMatrixElement> getElements() {
            return elements;
        }

        public void setElements(List<GoogleDistanceMatrixElement> elements) {
            this.elements = elements;
        }
    }
}

