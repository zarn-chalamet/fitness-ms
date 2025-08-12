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
      console.log(res.data);
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
      <Box sx={{ display: "flex", justifyContent: "center", mt: 3 }}>
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Paper elevation={3} sx={{ p: 3, mt: 3 }}>
      <Typography variant="h5" gutterBottom>
        My Activities
      </Typography>

      {activities.length === 0 ? (
        <Typography variant="body1" color="text.secondary">
          No activities found.
        </Typography>
      ) : (
        <List>
          {activities.map((activity, index) => (
            <React.Fragment key={activity.id || index}>
              <ListItem
                button
                onClick={() => navigate(`/activities/${activity.id}`)}
              >
                <ListItemText
                  primary={`${activity.type} - ${activity.duration} min`}
                  secondary={`Calories: ${activity.caloriesBurned} • Start: ${
                    activity.startTime
                      ? new Date(activity.startTime).toLocaleString()
                      : "N/A"
                  }`}
                />
              </ListItem>
              {index < activities.length - 1 && <Divider />}
            </React.Fragment>
          ))}
        </List>
      )}
    </Paper>
  );
};

export default ActivityList;
