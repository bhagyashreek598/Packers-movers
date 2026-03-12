import ServiceCard from "./ServiceCard";
import HomeWorkOutlinedIcon from "@mui/icons-material/HomeWorkOutlined";
import ApartmentOutlinedIcon from "@mui/icons-material/ApartmentOutlined";
import LocalShippingOutlinedIcon from "@mui/icons-material/LocalShippingOutlined";

const servicesData = [
  {
    id: 1,
    title: "Workspace Swift",
    badge: "COMMERCIAL MOVERS",
    description: "Extreme attention to detail is the essence of Boo’s unique.",
    image: "images/services/office-relocation.png",
    icon: HomeWorkOutlinedIcon,
  },
  {
    id: 2,
    title: "SafeTransit Auto",
    badge: "LOCAL MOVING",
    description: "Extreme attention to detail is the essence of Boo’s unique.",
    image: "images/services/vehicle.webp",
    icon: ApartmentOutlinedIcon,
  },
  {
    id: 3,
    title: "Residential Moves",
    badge: "RESIDENTIAL MOVING",
    description: "Extreme attention to detail is the essence of Boo’s unique.",
    image: "images/services/house-shifting.png",
    icon: LocalShippingOutlinedIcon,
  },
];

const ServicesSection = () => {
  return (
    <section id="services" className="bg-[#F2F2F2] py-20">
      <div className="max-w-7xl mx-auto px-15">
        {/* Header */}
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-10 mb-16">
          <div>
            <span className="inline-block text-sm font-semibold text-gray-900 border-b-2 border-yellow-400 mb-4">
              WHY CHOOSE US?
            </span>

            <h2 className="text-4xl font-light text-gray-900 leading-tight">
              We give you complete <br />
              <span className="font-extrabold">better services.</span>
            </h2>
          </div>

          <p className="text-gray-500 text-sm leading-relaxed max-w-lg">
            Our team discussed every single detail to make sure Boo is the most
            versatile and unique theme created so far.
          </p>
        </div>

        {/* Cards */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          {servicesData.map((service) => (
            <ServiceCard key={service.id} service={service} />
          ))}
        </div>
      </div>
    </section>
  );
};

export default ServicesSection;
