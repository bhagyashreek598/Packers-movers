import { FiChevronDown } from "react-icons/fi";

const scrollToQuoteForm = () => {
  const element = document.getElementById("quote");
  if (element) {
    element.scrollIntoView({ behavior: "smooth" });
  }
};

const HeroSection = () => {
  return (
    <div className="relative h-[650px] w-full">
      <img
        src="images/hero-img.png"
        className="absolute inset-0 h-full w-full object-cover hidden md:block"
        alt="hero-section-image"
      />
      {/* <img
        src="images/hero-img.png"
        className="absolute bottom-4 left-0.5  h-[100px] w-[100px] object-cover hidden md:block"
        alt="hero-section-image"
      /> */}
      {/* <img
        src="images/hero-img.png"
        alt="hero-section-image"
        style={{
          position: "absolute",
          bottom: "50px", // bottom-4
          left: "50%", // left-0.5 (2px)
          height: "100px",
          width: "100px",
          objectFit: "cover",
          zIndex: 5,
        }}
      /> */}

      <div
        onClick={scrollToQuoteForm}
        style={{
          width: "40px",
          height: "40px",
          borderRadius: "25px",
          border: "2px solid #1171BA",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
        className="group absolute bottom-[-28px] left-1/2 -translate-x-1/2 hidden md:block transition-transform duration-300 ease-out group-hover:translate-y-1"
      >
        <FiChevronDown className="h-10 w-10 text-[#1171BA] transition-transform duration-300 ease-out group-hover:translate-y-1" />
      </div>

      <img
        src="images/hero-img-mobile.png"
        className="absolute inset-0 h-full w-full object-cover block md:hidden"
        alt="hero-section-image"
      />

      {/* Overlay */}
      <div className="absolute inset-0 bg-brand-primary/70" />

      {/* Content */}
      <div className="relative z-10 h-full flex flex-col justify-center max-sm:justify-start max-sm:pt-[135px] px-12 md:px-24 text-[#1171BA] max-w-3xl">
        <h1 className="text-4xl md:text-6xl font-extrabold leading-tight jost">
          Intelligent Logistics.
          <br />
          Stable Transitions.
        </h1>

        <p className="mt-4 text-base md:text-lg text-[#0D3B66] font-display">
          More than just a move - it’s a strategically planned relocation. We
          combine precision handling with absolute reliability…
        </p>

        <button
          onClick={scrollToQuoteForm}
          className="mt-8 w-fit rounded-lg bg-[#1171BA] px-6 py-3 font-semibold text-white shadow-professional hover:bg-[#0A3D7A] transition"
        >
          Get a Free Quote now
        </button>
      </div>
    </div>
  );
};
export default HeroSection;
