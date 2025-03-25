import React from "react";

export default function Actor(props) {
  return (
    <div id="actorComponent">
      <p><b>Actors</b></p>
      {props.actors.map((actor, i) => (
          <p key={i}>{actor}</p>
      ))}
    </div>
  );
}
