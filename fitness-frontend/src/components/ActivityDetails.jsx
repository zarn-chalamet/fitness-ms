import React, { useEffect, useState } from "react";
import {
  Box,
  Typography,
  Paper,
  CircularProgress,
  Divider,
  List,
  ListItem,
  ListItemText,
} from "@mui/material";
import { useParams } from "react-router-dom";
import { getActivityDetails } from "../services/api";

const ActivityDetails = () => {
  const { id } = useParams();
  const [activity, setActivity] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchActivity = async () => {
    try {
      setLoading(true);
      const res = await getActivityDetails(id);
      setActivity(res.data);
    } catch (error) {
      console.error("Failed to fetch activity:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchActivity();
  }, [id]);

  if (loading) {
    return (
      <Box sx={{ display: "flex", justifyContent: "center", mt: 3 }}>
        <CircularProgress />
      </Box>
    );
  }

  if (!activity) {
    return (
      <Typography variant="body1" color="error">
        Activity not found.
      </Typography>
    );
  }

  // Helper to safely format createdAt
  const formatDate = (dateStr) =>
    dateStr ? new Date(dateStr).toLocaleString() : "N/A";

  // Split recommendation into sections by double newline for better readability
  const recommendationSections = activity.recommendation
    ? activity.recommendation.split("\n\n")
    : [];

  return (
    <Paper elevation={3} sx={{ p: 3, mt: 3, maxWidth: 800, mx: "auto" }}>
      <Typography variant="h4" gutterBottom>
        {activity.activityType}
      </Typography>
      <Typography variant="subtitle2" color="text.secondary" gutterBottom>
        Created at: {formatDate(activity.createdAt)}
      </Typography>

      <Divider sx={{ my: 2 }} />

      {/* Basic Info */}
      <Typography variant="body1" gutterBottom>
        <strong>Duration:</strong> {activity.duration} min
      </Typography>
      <Typography variant="body1" gutterBottom>
        <strong>Calories Burned:</strong> {activity.caloriesBurned}
      </Typography>
      <Typography variant="body1" gutterBottom>
        <strong>Start Time:</strong>{" "}
        {formatDate(activity.startTime)}
      </Typography>

      {/* Additional Metrics */}
      {activity.additionalMetrics && (
        <Box sx={{ mt: 3 }}>
          <Typography variant="h6">Additional Metrics</Typography>
          <Typography>Distance: {activity.additionalMetrics.distance} km</Typography>
          <Typography>
            Avg Speed: {activity.additionalMetrics.averageSpeed} km/h
          </Typography>
          <Typography>
            Max Heart Rate: {activity.additionalMetrics.maxHeartRate} bpm
          </Typography>
        </Box>
      )}

      <Divider sx={{ my: 3 }} />

      {/* Recommendation */}
      <Typography variant="h5" gutterBottom>
        Recommendation
      </Typography>
      {recommendationSections.map((section, idx) => (
        <Typography
          key={idx}
          variant="body2"
          sx={{ whiteSpace: "pre-line", mb: 1 }}
        >
          {section}
        </Typography>
      ))}

      {/* Improvements */}
      {activity.improvements?.length > 0 && (
        <>
          <Divider sx={{ my: 3 }} />
          <Typography variant="h6" gutterBottom>
            Improvements
          </Typography>
          <List dense>
            {activity.improvements.map((item, idx) => (
              <ListItem key={idx}>
                <ListItemText primary={item} />
              </ListItem>
            ))}
          </List>
        </>
      )}

      {/* Suggestions */}
      {activity.suggestions?.length > 0 && (
        <>
          <Divider sx={{ my: 3 }} />
          <Typography variant="h6" gutterBottom>
            Suggestions
          </Typography>
          <List dense>
            {activity.suggestions.map((item, idx) => (
              <ListItem key={idx}>
                <ListItemText primary={item} />
              </ListItem>
            ))}
          </List>
        </>
      )}

      {/* Safety Tips */}
      {activity.safety?.length > 0 && (
        <>
          <Divider sx={{ my: 3 }} />
          <Typography variant="h6" gutterBottom>
            Safety Tips
          </Typography>
          <List dense>
            {activity.safety.map((item, idx) => (
              <ListItem key={idx}>
                <ListItemText primary={item} />
              </ListItem>
            ))}
          </List>
        </>
      )}
    </Paper>
  );
};

export default ActivityDetails;
