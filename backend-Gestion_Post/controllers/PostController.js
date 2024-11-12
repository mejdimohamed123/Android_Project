import { validationResult } from "express-validator";
import Post from "../models/Post";

export function addPost(req,res){
    if (!validationResult(req).isEmpty()) {
        res.status(400).json({ errors: validationResult(req).array() });
      }
    else{
        Post.create({
            title: req.body.title,
            desc: req.body.desc,
            image: `${req.protocol}://${req.get("host")}/img/${req.file.filename}`,
            category: req.body.category,
            date: req.body.date,
            state: req.body.state
        })
        .then((p)=>{
            res.status(200).json({
                title: p.title,
                desc: p.desc,
                image: p.image,
                category: p.category,
                date: p.date,
                state: p.state
            })
        .catch(err =>{
            res.satus(500).json({error: err});
        });    
        })
}
}

export function updatePost(req,res){
    Post.findByIdAndUpdate(req.params.idPost,req.body)
        .then((p)=>{
            Post.findById(req.params.id)
                .then((p2)=>{
                    res.status(200).json(p2);
                })
                .catch(err =>{
                    res.satus(500).json({error: err});
                }); 
        })
        .catch(err =>{
            res.satus(500).json({error: err});
        }); 


    }
   


export function displayPosts(req,res){
    Post.find({})
        .then((docs)=>{
            let posts=[]; 
                for(let i=0;i<docs.length;i++){
                    posts.push({
                         id: docs[i].id,
                         title: docs[i].title,
                         desc:docs[i].desc,
                         image: docs[i].image,
                         category: docs[i].category,
                         date: docs[i].date,
                         state:docs[i].state,
                     });

        }
        res.status(200).json(posts);})
        .catch((err)=>{res.status(500).json({Error:err});
    });
}

export function findPost(req,res){
    Post.findOne({"title":req.params.title})
        .then(doc =>{
            res.status(200).json(doc);
        })
        .catch(err =>{
            res.satus(500).json({error: err});
        });
}

export function displayPostsByCategory(req,res){
    Post.find({"category":req.params.category})
        .then((docs)=>{
            let posts=[]; 
                for(let i=0;i<docs.length;i++){
                    posts.push({
                         id: docs[i].id,
                         title: docs[i].title,
                         desc:docs[i].desc,
                         image: docs[i].image,
                         category: docs[i].category,
                         date: docs[i].date,
                         state:docs[i].state,
                     });

        }
        res.status(200).json(posts);})
        .catch((err)=>{res.status(500).json({Error:err});
    });
}

export function deletePostById(req, res) {
    Post.findByIdAndDelete(req.params.id)
        .then(() => {
            res.status(200).json({ message: "Post deleted successfully" });
        })
        .catch((err) => {
            res.status(500).json({ error: err });
        });
}


