import React from "react";

export default function ActorSelect(props) {
  return (
    <div className={"actorSelect"}>
      <label htmlFor={"actor"}><b>Actor: </b></label>
      <input
        id={"actorInput"}
        type="text"
        onChange={(e) => props.fetchActorMovies(`${e.target.value}`)}
      />
    </div>
  );
}
