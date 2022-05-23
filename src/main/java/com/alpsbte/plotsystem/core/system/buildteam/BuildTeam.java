package com.alpsbte.plotsystem.core.system.buildteam;

import com.alpsbte.plotsystem.core.database.DatabaseConnection;
import com.alpsbte.plotsystem.core.system.Country;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BuildTeam {
    private final int ID;
    private final String name;
    private final APIKey apiKey;

    public BuildTeam(int id) throws SQLException {
        String name = null;
        APIKey apiKey = null;
        try (ResultSet rs = DatabaseConnection
                .createStatement("SELECT id, name, api_key FROM plotsystem_buildteams WHERE id = ?")
                .setValue(id)
                .executeQuery()) {

            if (rs.next()) {
                name = rs.getString(2);
                apiKey = new APIKey(rs.getString(3));
                if (rs.wasNull()) id = -1;
            }

            DatabaseConnection.closeResultSet(rs);
        }
        this.ID = id;
        this.name = name;
        this.apiKey = apiKey;
    }

    public int getID() { return ID; }

    public String getName() { return name; }

    public APIKey getApiKey() { return apiKey; }

    public boolean hasCountry(Country country) {
        try (ResultSet rs = DatabaseConnection
                .createStatement("SELECT id, country_id, buildteam_id FROM plotsystem_buildteam_has_countries WHERE buildteam_id = ? AND country_id = ?")
                .setValue(ID).setValue(country.getID())
                .executeQuery()) {

            if (rs.next()) {
                DatabaseConnection.closeResultSet(rs);
                return true;
            }

            DatabaseConnection.closeResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
