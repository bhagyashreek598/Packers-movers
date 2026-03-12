import React from "react";
import HeroSection from "../components/HeroSection";
import MovingProceduresUI from "../components/MovingProceduresUI";
import WeFocusQuality from "../components/WeFocusQuality";
import Container from "../components/common/Container";
import QuoteForm from "../components/QuoteForm";
import ServicesSection from "../components/ServicesSection";

const Home = () => {
  return (
    <div>
      <HeroSection />
      <MovingProceduresUI />

      {/* Wrapper */}
      <div className="relative 2xl:mb-[530px] mb-0">
        <WeFocusQuality />

        {/* Quote Form */}
        <div
          className="
            static
            2xl:absolute
            2xl:left-1/2
            2xl:top-[100%]
            2xl:w-full
            2xl:-translate-x-1/2
            2xl:-translate-y-1/2
            mt-12
            2xl:mt-[70px]

          "
        >
          <Container>
            <QuoteForm />
          </Container>
        </div>
      </div>
      {/* <QuoteForm /> */}
      <ServicesSection />
    </div>
  );
};

export default Home;
