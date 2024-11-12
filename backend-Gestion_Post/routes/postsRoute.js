const express = require('express');
const Post = require('../models/Post')
const router = express.Router();






router.post('/add-post', async (req, res) => {
    try {
        const newPost = new Post(req.body);
        await newPost.save();
        res.status(201).json({ message: 'Post added successfully', data: newPost });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Internal server error' });
    }
});

router.get('/get-all-posts', async (req, res) => {
    try {
      const posts = await Post.find({}, '-__v'); // Exclude the '__v' field from the response
      const formattedPosts = posts.map(post => ({ id: post._id, ...post.toObject() }));
  
      res.json(formattedPosts);
    } catch (error) {
      res.status(500).json(error);
    }
  });
router.get('/:id', async (req, res) => {
    try {
      const postId = req.params.id;
  
      // Find the post by its ID
      const post = await Post.findById(postId);
  
      // If the post doesn't exist, return an error response
      if (!post) {
        return res.status(404).json({ error: 'Post not found' });
      }
  
      // Return the post as the response
      res.json(post);
    } catch (error) {
      // If an error occurs, handle it and send an error response
      res.status(500).json({ error: error.message });
    }
  });

 
router.post('/edit-post', async function (req, res) {
    try {
        console.log(req.body.lastname)
        await Post.findOneAndUpdate({ _id: req.body.postId, }, req.body.payload)

        res.send('updated');
    } catch (error) {
        res.status(500).json(error)

    }

});

router.post('/delete-post', async function (req, res) {
    try {
        await Post.findOneAndDelete({ _id: req.body.postId })

        res.send('Deleted');
    } catch (error) {
        res.status(500).json(error)

    }

});
module.exports = router;