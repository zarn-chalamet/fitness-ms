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
  Divider,
} from '@mui/material';
import { addActivity } from "../services/api";

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

  const handleChange = (e) => {
    const { name, value } = e.target;
    setActivity((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

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
    <Paper
      elevation={6}
      sx={{
        p: 4,
        maxWidth: 600,
        mx: 'auto',
        mt: 6,
        borderRadius: 3,
        background: 'linear-gradient(145deg, #f9f9f9, #e0e0e0)',
      }}
    >
      <Typography
        variant="h4"
        mb={3}
        align="center"
        sx={{ fontWeight: 'bold', color: '#1976d2' }}
      >
        Log New Activity
      </Typography>

      <Box
        component="form"
        onSubmit={handleSubmit}
        sx={{
          display: 'flex',
          flexDirection: 'column',
          gap: 3,
        }}
      >
        {/* Activity Info */}
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
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

          <TextField
            label="Duration (minutes)"
            name="duration"
            type="number"
            value={activity.duration}
            onChange={handleChange}
            fullWidth
            sx={{ '& input': { color: '#333' } }}
          />

          <TextField
            label="Calories Burned"
            name="caloriesBurned"
            type="number"
            value={activity.caloriesBurned}
            onChange={handleChange}
            fullWidth
            sx={{ '& input': { color: '#333' } }}
          />

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
        </Box>

        <Divider sx={{ my: 2 }} />

        {/* Additional Metrics */}
        <Typography
          variant="h6"
          mb={1}
          sx={{ fontWeight: 500, color: '#1976d2' }}
        >
          Additional Metrics
        </Typography>
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
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
        </Box>

        <Button
          type="submit"
          variant="contained"
          sx={{
            mt: 3,
            py: 1.5,
            fontWeight: 'bold',
            fontSize: '1rem',
            backgroundColor: '#1976d2',
            '&:hover': {
              backgroundColor: '#115293',
            },
          }}
        >
          Add Activity
        </Button>
      </Box>
    </Paper>
  );
};

export default ActivityForm;
