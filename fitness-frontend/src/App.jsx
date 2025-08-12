import { BrowserRouter as Router, Navigate, Route, Routes } from "react-router-dom";
import { Box, Button } from "@mui/material";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "react-oauth2-code-pkce";
import { useDispatch } from "react-redux";
import { setCredentials } from "./store/authSlice";
import ActivityForm from "./components/ActivityForm";
import ActivityList from "./components/ActivityList";
import ActivityDetails from "./components/ActivityDetails";

const ActivityPage = () => (
  <Box component="section">
    <ActivityForm onActivityAdded={() => window.location.reload()} />
    <ActivityList />
  </Box>
);

function App() {
  const { token, tokenData, logIn } = useContext(AuthContext);
  const dispatch = useDispatch();
  const [authReady, setAuthReady] = useState(false);

  useEffect(() => {
    if (token) {
      dispatch(setCredentials({ token, user: tokenData }));
      setAuthReady(true);
    } else {
      setAuthReady(false);
    }
  }, [token, tokenData, dispatch]);

  // Optionally, wait for authReady to avoid flicker
  if (!authReady && !token) {
    // Show nothing or loading spinner if you want
    return null;
  }

  return (
    <Router>
      {!token ? (
        <Box sx={{ display: "flex", justifyContent: "center", mt: 4 }}>
          <Button
            variant="contained"
            sx={{
              backgroundColor: "#dc004e",
              "&:hover": { backgroundColor: "#b3003a" },
            }}
            onClick={() => logIn()}
          >
            LOGIN
          </Button>
        </Box>
      ) : (
        <Box component="section" sx={{ p: 2, border: "1px dashed grey" }}>
          <Routes>
            <Route path="/activities" element={<ActivityPage />} />
            <Route path="/activities/:id" element={<ActivityDetails />} />
            <Route
              path="/"
              element={<Navigate to="/activities" replace />}
            />
            {/* Optional: Add a catch-all 404 page */}
            {/* <Route path="*" element={<NotFound />} /> */}
          </Routes>
        </Box>
      )}
    </Router>
  );
}

export default App;
