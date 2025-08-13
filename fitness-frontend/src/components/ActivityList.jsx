import React, { useEffect, useState } from "react";
import {
  Box,
  Typography,
  Paper,
  CircularProgress,
  List,
  ListItem,
  ListItemText,
  Divider,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import { getActivities } from "../services/api";

const ActivityList = () => {
  const [activities, setActivities] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  const fetchActivities = async () => {
    try {
      setLoading(true);
      const res = await getActivities();
      setActivities(res.data);
    } catch (error) {
      console.error("Failed to fetch activities:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchActivities();
  }, []);

  if (loading) {
    return (
      <Box sx={{ display: "flex", justifyContent: "center", mt: 4 }}>
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Paper
      elevation={6}
      sx={{
        p: 4,
        mt: 6,
        mx: "auto",
        maxWidth: 600,
        borderRadius: 3,
        background: "linear-gradient(145deg, #f9f9f9, #e0e0e0)",
      }}
    >
      <Typography
        variant="h4"
        mb={3}
        align="center"
        sx={{ fontWeight: "bold", color: "#1976d2" }}
      >
        My Activities
      </Typography>

      {activities.length === 0 ? (
        <Typography variant="body1" color="text.secondary" align="center">
          No activities found.
        </Typography>
      ) : (
        <List sx={{ display: "flex", flexDirection: "column", gap: 1 }}>
          {activities.map((activity, index) => (
            <React.Fragment key={activity.id || index}>
              <ListItem
                button
                onClick={() => navigate(`/activities/${activity.id}`)}
                sx={{
                  borderRadius: 2,
                  mb: 1,
                  backgroundColor: "#fff",
                  boxShadow: 1,
                  "&:hover": {
                    backgroundColor: "#e3f2fd",
                    transform: "translateY(-2px)",
                  },
                  transition: "all 0.2s ease",
                }}
              >
                <ListItemText
                  primary={
                    <Typography
                      variant="subtitle1"
                      sx={{ fontWeight: 500, color: "#1976d2" }}
                    >
                      {activity.type} - {activity.duration} min
                    </Typography>
                  }
                  secondary={
                    <Typography variant="body2" color="text.secondary">
                      Calories: {activity.caloriesBurned} • Start:{" "}
                      {activity.startTime
                        ? new Date(activity.startTime).toLocaleString()
                        : "N/A"}
                    </Typography>
                  }
                />
              </ListItem>
              {index < activities.length - 1 && (
                <Divider sx={{ borderColor: "#b0bec5" }} />
              )}
            </React.Fragment>
          ))}
        </List>
      )}
    </Paper>
  );
};

export default ActivityList;
