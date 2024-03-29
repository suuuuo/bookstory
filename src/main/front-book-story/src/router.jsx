import { createBrowserRouter } from "react-router-dom";
import Footer from "./Components/Footer";

const router = createBrowserRouter([
  {
    path: "/",
    element: (
      <>
        <Footer />
      </>
    ),
  },

]);

export default router;
