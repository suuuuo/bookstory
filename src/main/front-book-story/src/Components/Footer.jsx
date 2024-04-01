export default function Footer() {
  return (
    <footer
      style={{
        border: "1px solid black",
        padding: "30px 0",
      }}
    >
       <div className="container">
              <div className="row">
                <div className="col-md-6">
                  <h5>BookStroy</h5>
                  <p>5명의 개발자가 함께 했습니다.<a href="#">Elice 2 Team GitLab</a></p>
                </div>
                <div className="col-md-3">
                  <p>Developer</p>
                  <ul className="list-unstyled">
                    <li><a href="#">깃허브 1</a></li>
                    <li><a href="#">깃허브 2</a></li>
                    <li><a href="#">깃허브 3</a></li>
                    <li><a href="#">깃허브 4</a></li>
                    <li><a href="#">깃허브 5</a></li>
                  </ul>
                </div>
                <div className="col-md-3">
                  <h5>Stack</h5>
                  <ul className="list-unstyled">
                    <li>Java</li>
                    <li>Java</li>
                    <li>Java</li>
                    <li>Java</li>
                    <li>Java</li>
                    <li>Java</li>
                    <li>Java</li>
                  </ul>
                </div>
              </div>
              <hr />
              <div className="row">
                <div className="col-md-12">
                  <p className="text-center">© {new Date().getFullYear()} Elice Cloud 2nd - 2 Team. All rights reserved.</p>
                </div>
              </div>
            </div>
    </footer>
  );
}
