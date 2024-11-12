const mongoose = require('mongoose');

const postSchema = new mongoose.Schema({
    
        title:{
            type: String,
            required: true
        },
        desc:{
            type: String,
            required:true
        },
        image:{
            type: String,
            required:false
        },
        category:{
            type: String,
            required:false
        },
        date: {
            type: String, // Consider using type: Date if you want to store actual date objects
            default: () => Date.now(), // Use a function to ensure it gets the current date at runtime
            required: true
        },
        state:{
            type: String,
            required:true
        }
    
        
    },
    {
        timestamps:true
    });

const postModel = mongoose.model('Post', postSchema);

module.exports = postModel;
