import HeroSection from "../components/HeroSection";
import MovingProceduresUI from "../components/MovingProceduresUI";
import WeFocusQuality from "../components/WeFocusQuality";
import Container from "../components/common/Container";
import QuoteForm from "../components/QuoteForm";
import ServicesSection from "../components/ServicesSection";

const Services = () => {
  return (
    <div>
      <HeroSection />
      <MovingProceduresUI />

      {/* Wrapper */}
      <div className="relative 2xl:mb-[550px] mb-0">
        <WeFocusQuality />

        {/* Quote Form */}
        <div
          className="
            static
            2xl:absolute
            2xl:left-1/2
            2xl:top-[140%]
            2xl:w-full
            2xl:-translate-x-1/2
            2xl:-translate-y-1/2
            mt-12
            2xl:mt-0
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

export default Services;
