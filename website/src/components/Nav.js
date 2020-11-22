import icon from "../assets/icon.png";

const Nav = (props) => {
  return (
    <nav
      className="navbar navbar-expand-lg navbar-light "
      style={{
        backgroundColor: "#FB5431",
      }}
    >
      <span className="mr-2">
        <img
          style={{
            width: "50px",
          }}
          src={icon}
        />
      </span>
      <a className="navbar-brand text-light mt-1" href="#">
        Shopee2
      </a>

      <div className="collapse navbar-collapse" id="navbarText">
        <ul className="navbar-nav mr-auto"></ul>
        <span className="navbar-text text-light">
          User management demo website (uid: {props.uid})
        </span>
      </div>
    </nav>
  );
};

export default Nav;
