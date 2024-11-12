import express from "express";
import { body } from "express-validator";

import  {addPost , updatePost, displayPosts,displayPostsByCategory,findPost,deletePostById} from '../controllers/PostController'; 

const router = express.Router(); 

router.post("/posts", addPost); 
router.put("/posts/:id", updatePost);
router.get("/posts", displayPosts); 
router.get("/posts/category/:category", displayPostsByCategory); 
router.get("/posts/:title", findPost); 
router.delete("/posts/:id", deletePostById); 

export default router ; 
