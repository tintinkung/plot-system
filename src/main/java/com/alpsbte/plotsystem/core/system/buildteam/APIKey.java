package com.alpsbte.plotsystem.core.system.buildteam;

import com.alpsbte.plotsystem.core.database.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class APIKey {
    private final String apiKey;
    private final Date createdAt;

    public APIKey(String apiKey) throws SQLException {
        Date createdAt = null;

        try (ResultSet rs = DatabaseConnection
                .createStatement("SELECT api_key, created_at FROM api_keys WHERE api_key = ?")
                .setValue(apiKey)
                .executeQuery()) {

            if (rs.next()) {
                createdAt = rs.getDate(2);
                if (rs.wasNull()) apiKey = "";
            }

            DatabaseConnection.closeResultSet(rs);
        }
        this.apiKey = apiKey;
        this.createdAt = createdAt;
    }

    public String getApiKey() { return apiKey; }

    public Date getCreatedAt() { return createdAt; }

    //    public boolean isValid() {
//        return createdAt != null && LocalDate.now().minusDays(0).isBefore(createdAt.toInstant()));
//    }
}
