import React, { useState } from 'react';
import {
  Box,
  Button,
  MenuItem,
  Select,
  TextField,
  InputLabel,
  FormControl,
  Typography,
  Paper,
} from '@mui/material';
import {addActivity} from "../services/api"

const ActivityForm = ({ onActivityAdded }) => {
  const [activity, setActivity] = useState({
    type: 'RUNNING',
    userId: '',
    duration: '',
    caloriesBurned: '',
    startTime: '',
    additionalMetrics: {
      distance: '',
      averageSpeed: '',
      maxHeartRate: '',
    },
  });

  // Handle top-level fields
  const handleChange = (e) => {
    const { name, value } = e.target;
    setActivity((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  // Handle additionalMetrics fields
  const handleAdditionalChange = (e) => {
    const { name, value } = e.target;
    setActivity((prev) => ({
      ...prev,
      additionalMetrics: {
        ...prev.additionalMetrics,
        [name]: value,
      },
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Convert number fields
    const finalData = {
      ...activity,
      duration: Number(activity.duration),
      caloriesBurned: Number(activity.caloriesBurned),
      additionalMetrics: {
        distance: Number(activity.additionalMetrics.distance),
        averageSpeed: Number(activity.additionalMetrics.averageSpeed),
        maxHeartRate: Number(activity.additionalMetrics.maxHeartRate),
      },
    };

    try {
      await addActivity(finalData);
      onActivityAdded();
      setActivity({
        type: 'RUNNING',
        duration: '',
        caloriesBurned: '',
        startTime: '',
        additionalMetrics: {
          distance: '',
          averageSpeed: '',
          maxHeartRate: '',
        },
      });
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Paper elevation={3} sx={{ p: 3, maxWidth: 500, mx: 'auto', mt: 4 }}>
      <Typography variant="h5" mb={2}>
        Log New Activity
      </Typography>
      <Box
        component="form"
        onSubmit={handleSubmit}
        sx={{
          display: 'flex',
          flexDirection: 'column',
          gap: 2,
        }}
      >
        {/* Activity Type */}
        <FormControl fullWidth>
          <InputLabel>Activity Type</InputLabel>
          <Select
            name="type"
            value={activity.type}
            label="Activity Type"
            onChange={handleChange}
          >
            <MenuItem value="RUNNING">Running</MenuItem>
            <MenuItem value="CYCLING">Cycling</MenuItem>
            <MenuItem value="SWIMMING">Swimming</MenuItem>
            <MenuItem value="WALKING">Walking</MenuItem>
          </Select>
        </FormControl>

        {/* Duration */}
        <TextField
          label="Duration (minutes)"
          name="duration"
          type="number"
          value={activity.duration}
          onChange={handleChange}
          fullWidth
        />

        {/* Calories */}
        <TextField
          label="Calories Burned"
          name="caloriesBurned"
          type="number"
          value={activity.caloriesBurned}
          onChange={handleChange}
          fullWidth
        />

        {/* Start Time */}
        <TextField
          label="Start Time"
          name="startTime"
          type="datetime-local"
          value={activity.startTime}
          onChange={handleChange}
          InputLabelProps={{
            shrink: true,
          }}
          fullWidth
        />

        {/* Additional Metrics */}
        <Typography variant="subtitle1" mt={1}>
          Additional Metrics
        </Typography>
        <TextField
          label="Distance (km)"
          name="distance"
          type="number"
          value={activity.additionalMetrics.distance}
          onChange={handleAdditionalChange}
          fullWidth
        />
        <TextField
          label="Average Speed (km/h)"
          name="averageSpeed"
          type="number"
          value={activity.additionalMetrics.averageSpeed}
          onChange={handleAdditionalChange}
          fullWidth
        />
        <TextField
          label="Max Heart Rate (bpm)"
          name="maxHeartRate"
          type="number"
          value={activity.additionalMetrics.maxHeartRate}
          onChange={handleAdditionalChange}
          fullWidth
        />

        <Button
          type="submit"
          variant="contained"
          color="primary"
          sx={{ mt: 1 }}
        >
          Add Activity
        </Button>
      </Box>
    </Paper>
  );
};

export default ActivityForm;
