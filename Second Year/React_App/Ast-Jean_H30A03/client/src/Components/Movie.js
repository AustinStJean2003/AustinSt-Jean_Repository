import React from "react";
import Actor from "./Actor";
import Genre from "./Genre";
import "../styles/movie.css";

export default function Movie(props) {
  return (
    <div className={"moviesDiv"}>
      <h3>{props.movie.Title}</h3>
      <div id="movieInfoLine">
        <p><b>Year</b><br></br> {props.movie.Year}</p>
        <p><b>Runtime</b><br></br> {props.movie.Runtime} minutes</p>
        <p><b>Revenue</b><br></br> ${props.movie.Revenue} Million</p>
      </div>
      <div className={"moviesInfoDiv"}>
        <Actor actors={props.movie.Actors} />
        <Genre genres={props.movie.Genre} />
      </div>
    </div>
  );
}
