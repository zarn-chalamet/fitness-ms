import { BrowserRouter as Router, Navigate, Route, Routes } from "react-router-dom";
import {
  Box,
  Button,
  Typography,
  Modal,
  Paper,
  Divider,
} from "@mui/material";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "react-oauth2-code-pkce";
import { useDispatch } from "react-redux";
import { setCredentials } from "./store/authSlice";
import ActivityForm from "./components/ActivityForm";
import ActivityList from "./components/ActivityList";
import ActivityDetails from "./components/ActivityDetails";
import { getUserData } from "./services/api";

const ActivityPage = () => (
  <Box
    sx={{
      display: "flex",
      flexDirection: { xs: "column", md: "row" },
      gap: 4,
      justifyContent: "center",
      mt: 4,
      px: 2,
    }}
  >
    <Box sx={{ flex: "0 0 48%", maxWidth: "48%" }}>
      <ActivityForm onActivityAdded={() => window.location.reload()} />
    </Box>
    <Box sx={{ flex: "0 0 48%", maxWidth: "48%" }}>
      <ActivityList />
    </Box>
  </Box>
);

function App() {
  const { token, tokenData, logIn, logOut } = useContext(AuthContext);
  const dispatch = useDispatch();
  const [authReady, setAuthReady] = useState(false);
  const [user, setUser] = useState(null);
  const [openProfile, setOpenProfile] = useState(false);

  useEffect(() => {
    if (token) {
      dispatch(setCredentials({ token, user: tokenData }));
      setAuthReady(true);
      fetchUser();
    } else {
      setAuthReady(false);
    }
  }, [token, tokenData, dispatch]);

  const fetchUser = async () => {
    try {
      const res = await getUserData();
      setUser(res.data);
    } catch (error) {
      console.error("Failed to fetch user data:", error);
    }
  };

  if (!authReady && !token) return null;

  return (
    <Router>
      {!token ? (
        <Box sx={{ display: "flex", justifyContent: "center", mt: 6 }}>
          <Button
            variant="contained"
            sx={{
              backgroundColor: "#1976d2",
              "&:hover": { backgroundColor: "#115293" },
              py: 1.5,
              px: 4,
              fontWeight: "bold",
              fontSize: "1rem",
            }}
            onClick={() => logIn()}
          >
            LOGIN
          </Button>
        </Box>
      ) : (
        <Box>
          {/* Top Bar */}
          <Box
            sx={{
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
              p: 2,
              backgroundColor: "#f5f5f5",
              borderBottom: "1px solid #ddd",
            }}
          >
            <Typography variant="h6" sx={{ color: "#1976d2", fontWeight: "bold" }}>
              Fitness Tracker
            </Typography>

            <Box sx={{ display: "flex", gap: 2 }}>
              <Button
                variant="contained"
                color="primary"
                sx={{ py: 1 }}
                onClick={() => setOpenProfile(true)}
              >
                Profile
              </Button>

              {/* <Button
                variant="contained"
                sx={{
                  backgroundColor: "#dc004e",
                  "&:hover": { backgroundColor: "#b3003a" },
                  py: 1,
                }}
                onClick={() => logOut()}
              >
                LOG OUT
              </Button> */}
            </Box>
          </Box>

          <Routes>
            <Route path="/activities" element={<ActivityPage />} />
            <Route path="/activities/:id" element={<ActivityDetails />} />
            <Route path="/" element={<Navigate to="/activities" replace />} />
          </Routes>

          {/* Profile Modal */}
          <Modal open={openProfile} onClose={() => setOpenProfile(false)}>
            <Box
              sx={{
                position: "absolute",
                top: "50%",
                left: "50%",
                transform: "translate(-50%, -50%)",
                width: 350,
                bgcolor: "background.paper",
                borderRadius: 3,
                boxShadow: 24,
                p: 4,
              }}
            >
              <Typography variant="h6" mb={2} sx={{ fontWeight: "bold" }}>
                User Profile
              </Typography>
              {user ? (
                <>
                  <Typography>
                    <strong>Name:</strong> {user.firstName} {user.lastName}
                  </Typography>
                  <Typography>
                    <strong>Email:</strong> {user.email}
                  </Typography>
                  <Typography>
                    <strong>Created At:</strong>{" "}
                    {new Date(user.createdAt).toLocaleString()}
                  </Typography>

                  <Divider sx={{ my: 2 }} />

                  <Button
                    variant="contained"
                    sx={{
                      width: "100%",
                      backgroundColor: "#dc004e",
                      "&:hover": { backgroundColor: "#b3003a" },
                    }}
                    onClick={() => {
                      logOut();
                      setOpenProfile(false);
                    }}
                  >
                    LOG OUT
                  </Button>
                </>
              ) : (
                <Typography>Loading...</Typography>
              )}
            </Box>
          </Modal>
        </Box>
      )}
    </Router>
  );
}

export default App;
